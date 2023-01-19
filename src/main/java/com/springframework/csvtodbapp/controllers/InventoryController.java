package com.springframework.csvtodbapp.controllers;

import com.springframework.csvtodbapp.domain.InventoryData;
import com.springframework.csvtodbapp.services.CSVFileService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/csv")
public class InventoryController {
    CSVFileService fileService;

    public InventoryController(CSVFileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/inventories")
    @ResponseStatus(HttpStatus.OK)
    public Page<InventoryData>getAllInventories(@RequestParam(required = false,defaultValue = "0") int offset,
                                                 @RequestParam(required = false,defaultValue = "10") int pageSize) {
//        try {
//            List<InventoryData> inventories = fileService.getAllInventories();
//
//            if (inventories.isEmpty()) {
//                return new ArrayList<InventoryData>();
//            }
//
//            return (inventories);
//        } catch (Exception e) {
//            return null;
//        }

        Page<InventoryData> data = fileService.getAllInventories(offset,pageSize);
        return data;
    }

}
