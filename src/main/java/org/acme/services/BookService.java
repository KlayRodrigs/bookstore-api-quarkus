package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Book;
import org.acme.repositories.BookRepository;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository bookRepository;

    public List<Book> getBooks() throws SQLException {
        return bookRepository.getBooks();
    }

    public void addBooks(List<Book> books) throws SQLException {
        bookRepository.addBooksOrBook(books);
    }

    public void updateBook(long id, Book book) throws SQLException {
        book.setId(id);
        bookRepository.updateBook(book);
    }

    public void deleteBook(long id) throws SQLException {
        bookRepository.deleteBook(id);
    }

    public Book getBookById(long id) throws SQLException {
        return bookRepository.getBookById(id);
    }
}
