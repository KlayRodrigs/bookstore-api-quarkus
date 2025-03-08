package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.BookDTO;
import org.acme.entities.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookRepository {
    @Inject
    protected DataSource dataSource;

    public Book getBookById(long id) throws SQLException {
        String sql = "SELECT isbn, title, author, published_date, publisher, description, category " +
                     "FROM book " +
                     "WHERE id = ?";

        Book book = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book = this.getBookFromResultSet(rs);
                }
            }
        }

        return book;
    }

    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT isbn, title, author, published_date, publisher, description, category " +
                     "FROM book";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(this.getBookFromResultSet(rs));
            }
        }

        return books;
    }

    public void saveBook(BookDTO bookDTO) throws SQLException {
        String sql = "INSERT INTO book (isbn, title, author, published_date, publisher, description, category) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {

            var stmt = this.createBook(bookDTO, connection, sql);
            stmt.executeUpdate();
        }
    }

    public void updateBook(Long id, BookDTO bookDTO) throws SQLException {
        String sql = "UPDATE book " +
                     "SET isbn = ?, title = ?, author = ?, published_date = ?, publisher = ?, description = ?, category = ? " +
                     "WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {

            var stmt = this.createBook(bookDTO, connection, sql);

            stmt.setLong(8, id);
            stmt.executeUpdate();
        }
    }

    public void deleteBook(long id) throws SQLException {
        String sql = "DELETE FROM book " +
                     "WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            stmt.executeUpdate();
        }
    }

    private Book getBookFromResultSet(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }

        Book book = new Book();
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublishedDate(rs.getDate("published_date"));
        book.setPublisher(rs.getString("publisher"));
        book.setDescription(rs.getString("description"));
        book.setCategory(rs.getString("category"));

        return book;
    }

    private PreparedStatement createBook(BookDTO bookDTO, Connection connection, String sql) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, bookDTO.isbn());
        stmt.setString(2, bookDTO.title());
        stmt.setString(3, bookDTO.author());
        stmt.setDate(4, bookDTO.publishedDate());
        stmt.setString(5, bookDTO.publisher());
        stmt.setString(6, bookDTO.description());
        stmt.setString(7, bookDTO.category());

        return stmt;
    }
}
