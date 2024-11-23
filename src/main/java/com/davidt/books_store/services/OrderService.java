package com.davidt.books_store.services;

import com.davidt.books_store.entities.Order;
import com.davidt.books_store.dtos.OrderRequest;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest) throws Exception;
}
