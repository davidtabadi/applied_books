package com.davidt.books_store.controllers;

import com.davidt.books_store.dtos.OrderRequest;
import com.davidt.books_store.entities.Order;
import com.davidt.books_store.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }
}