package com.arendt.orderservice.model.dto;

import com.arendt.orderservice.model.entity.Order;
import com.arendt.orderservice.model.entity.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private List<OrderLineItem> orderLineItemsList;

    public static OrderResponse from(final Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderLineItemsList()
        );
    }
}
