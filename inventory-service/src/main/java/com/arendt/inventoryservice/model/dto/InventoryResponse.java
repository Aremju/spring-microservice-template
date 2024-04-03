package com.arendt.inventoryservice.model.dto;

import com.arendt.inventoryservice.model.Entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;

    public static InventoryResponse from(final Inventory inventory) {
        return new InventoryResponse(
                inventory.getSkuCode(),
                inventory.getQuantity() > 0
        );
    }
}
