package com.springframework.csvtodbapp.controllers;

import com.springframework.csvtodbapp.domain.InventoryData;
import com.springframework.csvtodbapp.services.CSVFileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/csv")
public class SupplierController {
    CSVFileService fileService;

    public SupplierController(CSVFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/supplier/{supplier_name}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryData> getAllStocks(@PathVariable String supplier_name,
                                            @RequestParam(required = false,defaultValue = "0") int offset,
                                            @RequestParam(required = false,defaultValue = "10") int pageSize){
        return fileService.getDataBySupplierName(supplier_name,offset,pageSize);
    }

    @GetMapping(value = "/supplier/{supplier_name}",params = {"product_name"})
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryData> getStocksAfterSearch(@PathVariable String supplier_name,
                                                    @RequestParam(value = "product_name",required = false) String product_name,
                                                    @RequestParam(required = false,defaultValue = "0")int offset,
                                                    @RequestParam(required = false,defaultValue = "10")int pageSize){
        return fileService.getDataBySearch(supplier_name,product_name,offset,pageSize);
    }

    @GetMapping(value = "/supplier/{supplier_name}",params = {"not_expired"})
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryData> getStocksAfterSearch(@PathVariable String supplier_name,
                                                    @RequestParam("not_expired") Boolean not_expired,
                                                    @RequestParam(required = false,defaultValue = "0")int offset,
                                                    @RequestParam(required = false,defaultValue = "10")int pageSize){

        return fileService.getNonExpiredDataBySupplierName(supplier_name,not_expired,offset,pageSize);

    }

    @GetMapping(value="/supplier/{supplier_name}",params={"product_name","not_expired"})
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryData> getNotExpiredSearchedStocks(@PathVariable String supplier_name,
                                                           @RequestParam("product_name") String product_name,
                                                           @RequestParam("not_expired") Boolean not_expired,
                                                           @RequestParam(required = false,defaultValue = "0")int offset,
                                                           @RequestParam(required = false,defaultValue = "10")int pageSize){

        return fileService.getNotExpiredSearchedInventories(supplier_name,product_name,not_expired,offset,pageSize);
    }

//    @GetMapping(value="/supplier/{supplier_name}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<InventoryData> getParameterisedData(@PathVariable String supplier_name,
//                                                    @RequestParam(required = false,defaultValue = "") String product_name,
//                                                    @RequestParam(required = false,defaultValue = "false") Boolean not_expired){
//        return fileService.getDataByParameters(supplier_name,product_name,not_expired);
//
//
//    }



}
