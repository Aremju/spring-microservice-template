package com.arendt.orderservice.controller;

import com.arendt.orderservice.model.dto.OrderRequest;
import com.arendt.orderservice.model.dto.OrderResponse;
import com.arendt.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody final OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.findAllOrders();
    }
}
