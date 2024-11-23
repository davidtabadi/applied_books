package com.davidt.books_store.controllers;

import com.davidt.books_store.entities.Author;
import com.davidt.books_store.entities.Book;
import com.davidt.books_store.exceptions.ResourceNotFoundException;
import com.davidt.books_store.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookServiceImpl bookService;

    @InjectMocks
    BookController bookController;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookById_Success() throws Exception {
        Book mockBook = new Book();
        mockBook.setTitle("Book Title");
        mockBook.setAuthor(new Author("Author Name"));
        mockBook.setPrice(3);
        mockBook.setAmount(20);

        // Arrange
        when(bookService.getBookById(1L)).thenReturn(mockBook);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 1L)) // No casting
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.name").value("Author Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(3));

        // Verify the service method was called once
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_Failure_BookNotFound() throws Exception {
        // Arrange
        when(bookService.getBookById(999L)).thenThrow(new ResourceNotFoundException("Book not found with ID: 999"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 999L))
                .andExpect(status().isNotFound());

        // Verify the service method was called once
        verify(bookService, times(1)).getBookById(999L);
    }

    @Test
    void getAllBooks_Success() throws Exception {

        Book mockBook1 = new Book();
        mockBook1.setTitle("Book1");
        mockBook1.setAuthor(new Author("Author1"));
        mockBook1.setPrice(3);
        mockBook1.setAmount(15);

        Book mockBook2 = new Book();
        mockBook2.setTitle("Book2");
        mockBook2.setAuthor(new Author("Author2"));
        mockBook2.setPrice(7);
        mockBook2.setAmount(30);

        // Arrange
        when(bookService.getAllBooks()).thenReturn(List.of(mockBook1, mockBook2));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value(30));

//        // Verify service method is called
        verify(bookService, times(1)).getAllBooks();
    }

}