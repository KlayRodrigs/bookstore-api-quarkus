package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookRepository {
    @Inject
    protected DataSource dataSource;

    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, isbn, title, author, published_date, publisher, description, quantity FROM book";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublished_date(rs.getDate("published_date"));
                book.setPublisher(rs.getString("publisher"));
                book.setDescription(rs.getString("description"));
                book.setQuantity(rs.getInt("quantity"));
                books.add(book);
            }
        }

        return books;
    }

    public void addBooksOrBook(Object input) throws SQLException {
        String sql = "INSERT INTO book(id, isbn, title, author, published_date, publisher, description, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (input instanceof List) {
                @SuppressWarnings("unchecked")
                final List<Book> books = (List<Book>) input;
                for (Book book : books) {
                    stmt.setLong(1, book.getId());
                    stmt.setString(2, book.getIsbn());
                    stmt.setString(3, book.getTitle());
                    stmt.setString(4, book.getAuthor());
                    if (book.getPublished_date() != null) {
                        stmt.setDate(5, new java.sql.Date(book.getPublished_date().getTime()));
                    } else {
                        stmt.setNull(5, Types.DATE);
                    }
                    stmt.setString(6, book.getPublisher());
                    stmt.setString(7, book.getDescription());
                    stmt.setInt(8, book.getQuantity());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            } else if (input instanceof Book book) {
                stmt.setLong(1, book.getId());
                stmt.setString(2, book.getIsbn());
                stmt.setString(3, book.getTitle());
                stmt.setString(4, book.getAuthor());
                if (book.getPublished_date() != null) {
                    stmt.setDate(5, new java.sql.Date(book.getPublished_date().getTime()));
                } else {
                    stmt.setNull(5, Types.DATE);
                }
                stmt.setString(6, book.getPublisher());
                stmt.setString(7, book.getDescription());
                stmt.setInt(8, book.getQuantity());
                stmt.executeUpdate();
            }
        }
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET isbn = ?, title = ?, author = ?, published_date = ?, publisher = ?, description = ?, quantity = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            if (book.getPublished_date() != null) {
                stmt.setDate(4, new java.sql.Date(book.getPublished_date().getTime()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            stmt.setString(5, book.getPublisher());
            stmt.setString(6, book.getDescription());
            stmt.setInt(7, book.getQuantity());
            stmt.setLong(8, book.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteBook(long id) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Book getBookById(long id) throws SQLException {
        String sql = "SELECT id, isbn, title, author, published_date, publisher, description, quantity FROM book WHERE id = ?";
        Book book = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublished_date(rs.getDate("published_date"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setDescription(rs.getString("description"));
                    book.setQuantity(rs.getInt("quantity"));
                }
            }
        }

        return book;
    }
}
