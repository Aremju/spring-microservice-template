package com.arendt.orderservice.service;

import com.arendt.orderservice.model.dto.OrderRequest;
import com.arendt.orderservice.model.entity.Order;
import com.arendt.orderservice.model.dto.OrderResponse;
import com.arendt.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(final OrderRequest orderRequest) {
        final var order = Order.from(orderRequest);
        orderRepository.save(order);
    }

    public List<OrderResponse> findAllOrders() {
        final var orderList = orderRepository.findAll();

        return orderList.stream()
                .map(OrderResponse::from)
                .toList();
    }
}
