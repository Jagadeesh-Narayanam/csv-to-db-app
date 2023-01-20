package com.springframework.csvtodbapp.services;

import com.springframework.csvtodbapp.helpers.CSVHelper;
import com.springframework.csvtodbapp.domain.InventoryData;
import com.springframework.csvtodbapp.repositories.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVFileService {

    private final InventoryRepository inventoryRepository;

    public CSVFileService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void save(MultipartFile file){
        try{
            List<InventoryData> inventories = CSVHelper.csvToInventories(file.getInputStream());
            inventoryRepository.deleteAll();
            inventoryRepository.saveAll(inventories);
        }
        catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }
    public Page<InventoryData> getData(String supplierName,String productName,Boolean notExpired,int offset,int pageSize){

        if(productName.length()==0 && notExpired==false){
            return inventoryRepository.findAllBySupplier(supplierName,PageRequest.of(offset,pageSize));
        }
        else if (productName.length()==0 && notExpired) {
            return inventoryRepository.findByExpiredOrNot(supplierName,notExpired,PageRequest.of(offset,pageSize));
        }
        else if (notExpired==false) {
            return inventoryRepository.findBySearch(supplierName,productName,PageRequest.of(offset,pageSize));
        }
        else{
            return inventoryRepository.findBySearchAndExpiredStatus(supplierName,productName,notExpired,PageRequest.of(offset,pageSize));
        }
    }
}
