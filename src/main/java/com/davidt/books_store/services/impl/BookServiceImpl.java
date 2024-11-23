package com.davidt.books_store.services.impl;

import com.davidt.books_store.entities.Book;
import com.davidt.books_store.exceptions.ResourceNotFoundException;
import com.davidt.books_store.repositories.BookRepository;
import com.davidt.books_store.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

}
