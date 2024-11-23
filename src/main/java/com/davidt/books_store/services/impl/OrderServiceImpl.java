package com.davidt.books_store.services.impl;

import com.davidt.books_store.dtos.OrderRequest;
import com.davidt.books_store.exceptions.InsufficientStockException;
import com.davidt.books_store.exceptions.ResourceNotFoundException;
import com.davidt.books_store.repositories.BookRepository;
import com.davidt.books_store.repositories.OrderRepository;
import com.davidt.books_store.services.OrderService;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import com.davidt.books_store.entities.Book;
import com.davidt.books_store.entities.Order;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public Order placeOrder(OrderRequest orderRequest) {

        Book book = bookRepository.findById(orderRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + orderRequest.getBookId()));

        if (book.getAmount() < orderRequest.getBookQuantity()) {
            throw new InsufficientStockException("Not enough stock for book: " + book.getTitle());
        }

        book.setAmount(book.getAmount() - orderRequest.getBookQuantity());
        bookRepository.save(book);

        Order order = new Order(orderRequest.getBookId(), orderRequest.getBookQuantity());
        return orderRepository.save(order);
    }
}
