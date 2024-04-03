package com.arendt.inventoryservice.repository;

import com.arendt.inventoryservice.model.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(final String skuCode);
}
