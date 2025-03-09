package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.BookDTO;
import org.acme.entities.Book;
import org.acme.repositories.BookRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository bookRepository;

    public int countBooks() throws SQLException {
        return bookRepository.countBooks();
    }

    public Book getBookById(Long id) throws SQLException {
        return bookRepository.getBookById(id);
    }

    public Optional<Book> getBookByIsbn(String isbn) throws SQLException {
        return Optional.of(bookRepository.getBookByIsbn(isbn));
    }

    public List<Book> getBooks() throws SQLException {
        return bookRepository.getBooks();
    }

    public Long saveBook(BookDTO bookDTO) throws SQLException {
        Long newBookId = bookRepository.saveBook(bookDTO);
        return newBookId;
    }

    public void updateBook(Long id, BookDTO bookDTO) throws SQLException {
        bookRepository.updateBook(id, bookDTO);
    }

    public void deleteBook(Long id) throws SQLException {
        bookRepository.deleteBook(id);
    }
}
