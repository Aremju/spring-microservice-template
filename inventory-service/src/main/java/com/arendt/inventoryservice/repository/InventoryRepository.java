package com.arendt.inventoryservice.repository;

import com.arendt.inventoryservice.model.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findBySkuCodeIn(final List<String> skuCodes);
}
