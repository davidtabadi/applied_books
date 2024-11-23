package com.davidt.books_store;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Book Store API", version = "1.0", description = "API for managing books and orders"))
@SpringBootApplication
public class BooksStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksStoreApplication.class, args);
	}

}
