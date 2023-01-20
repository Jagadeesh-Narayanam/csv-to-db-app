package com.springframework.csvtodbapp.repositories;

import com.springframework.csvtodbapp.domain.InventoryData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryData,Long> {
    @Query("select d from InventoryData d where d.supplier=?1 and d.stock>0")
    Page<InventoryData> findAllBySupplier(String supplier, PageRequest of);

    @Query("select d from InventoryData d where d.supplier=?1 and d.stock>0 and d.name=?2")
    Page<InventoryData> findBySearch(String supplier, String product, PageRequest of);

    @Query("select d from InventoryData d where d.supplier=?1 and d.stock>0 and ?2=true and d.expiry>NOW()")
    Page<InventoryData> findByExpiredOrNot(String supplier, Boolean not_expired, PageRequest of);

    @Query("select d from InventoryData d where d.supplier=?1 and d.stock>0 and d.name=?2 and ?3=true and d.expiry>NOW()")
    Page<InventoryData> findBySearchAndExpiredStatus(String supplier,String product,Boolean not_expired,PageRequest of);





}
