package org.acme.dtos;

import org.acme.entities.Book;

import java.sql.Date;


public record BookDTO(Long id, String isbn, String title, String description, String category, String author, String publisher, Date publishedDate, String imageUrl) {
    public static BookDTO fromBook(Book book) {
        return new BookDTO(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getDescription(),
                book.getCategory(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getImageUrl()
        );
    }
}
