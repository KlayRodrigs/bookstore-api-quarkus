package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.BookDTO;
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

    public void saveBook(BookDTO bookDTO) throws SQLException {
        bookRepository.saveBook(bookDTO);
    }

    public void updateBook(Long id, BookDTO bookDTO) throws SQLException {
        bookRepository.updateBook(id, bookDTO);
    }

    public void deleteBook(Long id) throws SQLException {
        bookRepository.deleteBook(id);
    }

    public Book getBookById(Long id) throws SQLException {
        return bookRepository.getBookById(id);
    }
}
