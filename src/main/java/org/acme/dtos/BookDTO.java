package org.acme.dtos;

import org.acme.entities.Book;

import java.sql.Date;


public record BookDTO(String isbn, String title, String description, String category, String author, String publisher, Date publishedDate) {
    public static BookDTO fromBook(Book book) {
        return new BookDTO(
                book.getIsbn(),
                book.getTitle(),
                book.getDescription(),
                book.getCategory(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublishedDate()
        );
    }
}
