package com.davidt.books_store.controllers;

import com.davidt.books_store.dtos.OrderRequest;
import com.davidt.books_store.entities.Order;
import com.davidt.books_store.exceptions.ResourceNotFoundException;
import com.davidt.books_store.services.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderServiceImpl orderService;

    @Test
    void placeOrder_ShouldReturn_status_ok() throws Exception {

        OrderRequest orderRequest = new OrderRequest(1L, 5);
        Order orderResponse = new Order(1L, 5);

        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(orderResponse);

        mockMvc.perform(post("/api/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void placeOrder_Success() throws Exception {
        // Arrange
        OrderRequest orderRequest = new OrderRequest(1L, 2);
        String orderRequestJson = new ObjectMapper().writeValueAsString(orderRequest);

        Order mockOrder = new Order(1L,  2);
        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(mockOrder);

        // Act & Assert
        mockMvc.perform(post("/api/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(status().isOk());

        // Verify service was called once
        verify(orderService, times(1)).placeOrder(any(OrderRequest.class));
    }

    @Test
    void placeOrder_Failure_BookNotFound() throws Exception {
        // Arrange
        OrderRequest orderRequest = new OrderRequest(999L, 1);
        String orderRequestJson = new ObjectMapper().writeValueAsString(orderRequest);

        when(orderService.placeOrder(any(OrderRequest.class)))
                .thenThrow(new ResourceNotFoundException("Book not found with ID: 999"));

        // Act & Assert
        mockMvc.perform(post("/api/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderRequestJson))
                .andExpect(status().isNotFound());

        // Verify service was called once
        verify(orderService, times(1)).placeOrder(any(OrderRequest.class));
    }

}