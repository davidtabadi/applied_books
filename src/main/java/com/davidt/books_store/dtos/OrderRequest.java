package com.davidt.books_store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    private Long bookId;
    private int bookQuantity;
}
