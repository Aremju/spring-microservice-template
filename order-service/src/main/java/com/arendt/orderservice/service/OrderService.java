package com.arendt.orderservice.service;

import com.arendt.orderservice.model.dto.InventoryResponse;
import com.arendt.orderservice.model.dto.OrderRequest;
import com.arendt.orderservice.model.dto.OrderResponse;
import com.arendt.orderservice.model.entity.Order;
import com.arendt.orderservice.model.entity.OrderLineItem;
import com.arendt.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(final OrderRequest orderRequest) {
        final var order = Order.from(orderRequest);

        final var skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        final var inventoryResponseArray = webClient.get()
                .uri("http://localhost:8001/api/inventories", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponseArray != null;
        final var allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new IllegalArgumentException("Product is not in Stock. Please try again later");
        }

        orderRepository.save(order);
    }

    public List<OrderResponse> findAllOrders() {
        final var orderList = orderRepository.findAll();

        return orderList.stream()
                .map(OrderResponse::from)
                .toList();
    }
}
