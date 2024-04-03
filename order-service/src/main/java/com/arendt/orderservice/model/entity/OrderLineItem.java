package com.arendt.orderservice.model.entity;

import com.arendt.orderservice.model.dto.OrderLineItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String skuCode;
    private double price;
    private Integer quantity;

    public static OrderLineItem from(final OrderLineItemDto orderLineItemDto) {
        return new OrderLineItem(
                orderLineItemDto.getId(),
                orderLineItemDto.getSkuCode(),
                orderLineItemDto.getPrice(),
                orderLineItemDto.getQuantity()
        );
    }
}
