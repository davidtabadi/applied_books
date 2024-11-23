package com.davidt.books_store.services;

import com.davidt.books_store.entities.Author;
import com.davidt.books_store.entities.Book;
import com.davidt.books_store.exceptions.ResourceNotFoundException;
import com.davidt.books_store.repositories.BookRepository;
import com.davidt.books_store.services.impl.BookServiceImpl;
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
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getBookById_Success() throws Exception {
        Book book = new Book();
        book.setTitle("Spring Guide");
        book.setAuthor(new Author("John Doe"));
        book.setPrice(10);
        book.setAmount(200);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book foundBook = bookService.getBookById(1L);
        assertNotNull(foundBook);
        assertEquals(null, foundBook.getId());
        assertEquals("Spring Guide", foundBook.getTitle());
    }

    @Test
    void getBookById_Failure() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void getAllBooks_Success() {

        Book book1 = new Book();
        book1.setTitle("Spring Guide");
        book1.setAuthor(new Author("John Doe"));
        book1.setPrice(10);
        book1.setAmount(200);

        Book book2 = new Book();
        book2.setTitle("Hibernate Mastery");
        book2.setAuthor(new Author("Jane Smith"));
        book2.setPrice(5);
        book2.setAmount(150);


        List<Book> books = List.of(book1,book2);

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> allBooks = bookService.getAllBooks();
        assertNotNull(allBooks);
        assertEquals(2, allBooks.size());
    }

}
