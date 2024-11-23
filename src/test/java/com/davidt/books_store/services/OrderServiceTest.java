package com.davidt.books_store.services;

import com.davidt.books_store.dtos.OrderRequest;
import com.davidt.books_store.entities.Author;
import com.davidt.books_store.entities.Book;
import com.davidt.books_store.entities.Order;
import com.davidt.books_store.exceptions.ResourceNotFoundException;
import com.davidt.books_store.repositories.BookRepository;
import com.davidt.books_store.repositories.OrderRepository;
import com.davidt.books_store.services.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private BookRepository bookRepository;


    @Test
    void placeOrder_StatusOk() {

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor(new Author("John Doe"));
        book.setPrice(10);
        book.setAmount(200);

        OrderRequest orderRequest = new OrderRequest(1L, 5);
        Order order = new Order(1L, 5);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order response = orderService.placeOrder(orderRequest);

        // Assert
        assertNotNull(response);
        assertEquals(null, response.getId());
        assertEquals(5, response.getBookAmount());
        verify(bookRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_BookNotFound() {
        // Arrange
        OrderRequest orderRequest = new OrderRequest(1L, 5);

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> orderService.placeOrder(orderRequest));
        verify(bookRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }


    @Test
    void testPlaceOrder_InsufficientStock() {
        Author author = new Author("John Doe");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Guide");
        book.setAmount(1);
        book.setPrice(200.0);
        book.setAuthor(author);

        author.setBooks(List.of(book));
        bookRepository.save(book);

        OrderRequest orderRequest = new OrderRequest(1L, 2);
        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(orderRequest));
    }

}
