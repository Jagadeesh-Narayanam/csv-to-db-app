package com.springframework.csvtodbapp.services;

import com.springframework.csvtodbapp.helpers.CSVHelper;
import com.springframework.csvtodbapp.domain.InventoryData;
import com.springframework.csvtodbapp.repositories.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
            inventoryRepository.saveAll(inventories);
        }
        catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }
    public Page<InventoryData> getAllInventories(int offset, int pageSize){

        Page<InventoryData> inventoryDataPage = inventoryRepository.findAll(PageRequest.of(offset, pageSize));
        return inventoryDataPage;
    }
    public List<InventoryData> getDataBySupplierName(String supplierName,int offset,int pageSize){
        List<InventoryData> inventories = inventoryRepository.findAll();
        List<InventoryData> requiredInventories = new ArrayList<>();
        for(InventoryData inventory:inventories){
            if(inventory.getSupplier().equals(supplierName)){
                if(inventory.getStock()>0){
                    requiredInventories.add(inventory);
                }
            }
        }
        return pageSplitter(requiredInventories,offset,pageSize);
    }
    public List<InventoryData> getDataBySearch(String supplierName,String productName,int offset,int pageSize){
        List<InventoryData> inventories = inventoryRepository.findAll();
        List<InventoryData> requiredInventories = new ArrayList<>();
        for(InventoryData inventory:inventories){
            if(inventory.getSupplier().equals(supplierName) && inventory.getStock()>0 && inventory.getName().equals(productName)){
                requiredInventories.add(inventory);
            }
        }
        return pageSplitter(requiredInventories,offset,pageSize);
    }
    public List<InventoryData> getNonExpiredDataBySupplierName(String supplierName,Boolean notExpired,int offset,int pageSize) {
        List<InventoryData> inventories= inventoryRepository.findAll();
        List<InventoryData> requiredInventories = new ArrayList<>();
        for(InventoryData inventory:inventories){
            if(inventory.getStock()>0 && inventory.getSupplier().equals(supplierName)) {
                expiryCheck(notExpired, requiredInventories, inventory);
            }

        }
        return pageSplitter(requiredInventories,offset,pageSize);
    }

    private void expiryCheck(Boolean notExpired, List<InventoryData> requiredInventories, InventoryData inventory) {
        if (notExpired) {
            try {
                Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(LocalDate.now()));
                if(inventory.getExpiry()!=null) {
                    if (currentDate.compareTo(inventory.getExpiry()) < 0) {
                        requiredInventories.add(inventory);
                    }
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            requiredInventories.add(inventory);
        }
    }

    public List<InventoryData> getNotExpiredSearchedInventories(String supplier_name,String product_name,Boolean not_expired,int offset,int pageSize){
        List<InventoryData> inventories= inventoryRepository.findAll();
        List<InventoryData> requiredInventories = new ArrayList<>();
        for(InventoryData inventoryData:inventories){
            if(inventoryData.getStock()>0 && inventoryData.getSupplier().equals(supplier_name) && inventoryData.getName().equals(product_name)){
                expiryCheck(not_expired, requiredInventories, inventoryData);
            }
        }
        return pageSplitter(requiredInventories,offset,pageSize);
    }

    public List<InventoryData> pageSplitter(List<InventoryData> inventories,int offset,int pageSize){
        int start=offset*pageSize;
        if(start > inventories.size()){
            throw new RuntimeException("Page unavailable");
        }
        int end= inventories.size()>(offset+1)*(pageSize)+1?(offset+1)*(pageSize):inventories.size();
        return inventories.subList(start,end);
    }
}
