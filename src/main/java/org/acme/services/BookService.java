package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Books;
import org.acme.repositories.BookRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository bookRepository;

    public List<Books> getBooks() throws SQLException {
        return bookRepository.getBooks();
    }

    public void addBook(Books book) throws SQLException {
        bookRepository.addBook(book);
    }
}
