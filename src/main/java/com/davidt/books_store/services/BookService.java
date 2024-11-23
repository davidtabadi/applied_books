package com.davidt.books_store.services;

import com.davidt.books_store.entities.Book;

import java.util.List;

public interface BookService {
    Book getBookById(Long id) throws Exception;
    List<Book> getAllBooks();
}
