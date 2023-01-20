package com.springframework.csvtodbapp.controllers;

import com.springframework.csvtodbapp.domain.InventoryData;
import com.springframework.csvtodbapp.services.CSVFileService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/csv")
public class SupplierController {
    CSVFileService fileService;

    public SupplierController(CSVFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value="/supplier/{supplier_name}")
    @ResponseStatus(HttpStatus.OK)
    public Page<InventoryData> getData(@PathVariable String supplier_name,
                                       @RequestParam(required = false,defaultValue = "") String product_name,
                                       @RequestParam(required = false,defaultValue = "false") Boolean not_expired,
                                       @RequestParam(required = false,defaultValue = "0")int offset,
                                       @RequestParam(required = false,defaultValue = "10")int pageSize){

        return fileService.getData(supplier_name,product_name,not_expired,offset,pageSize);

    }



}
