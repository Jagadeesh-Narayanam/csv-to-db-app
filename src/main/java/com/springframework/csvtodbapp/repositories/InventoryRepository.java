package com.springframework.csvtodbapp.repositories;

import com.springframework.csvtodbapp.domain.InventoryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryData,Long> {
}
