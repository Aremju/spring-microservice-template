package com.arendt.inventoryservice;

import com.arendt.inventoryservice.model.Entity.Inventory;
import com.arendt.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(final InventoryRepository inventoryRepository) {
        return args -> {
            final var inventory = new Inventory();
            inventory.setSkuCode("iphone_13");
            inventory.setQuantity(100);

            final var inventory1 = new Inventory();
            inventory1.setQuantity(0);
            inventory1.setSkuCode("iphone_13_red");

            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory);
        };
    }

}
