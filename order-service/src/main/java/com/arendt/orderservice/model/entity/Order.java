package com.arendt.orderservice.model.entity;

import com.arendt.orderservice.model.dto.OrderRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItemsList;

    public static Order from(final OrderRequest orderRequest) {
        return new Order(
                null,
                UUID.randomUUID().toString(),
                orderRequest.getOrderLineItemDtoList().stream()
                        .map(OrderLineItem::from)
                        .toList()
        );
    }
}
