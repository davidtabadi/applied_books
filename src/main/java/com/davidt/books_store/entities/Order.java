package com.davidt.books_store.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Long bookId;
    private int bookAmount;

    public Order(Long bookId, int bookAmount) {
        this.bookId = bookId;
        this.bookAmount = bookAmount;
    }
}
