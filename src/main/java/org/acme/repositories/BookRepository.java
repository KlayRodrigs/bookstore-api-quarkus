package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Books;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookRepository {
    @Inject
     protected DataSource dataSource;

    public List<Books> getBooks() throws SQLException {
        List<Books> books = new ArrayList<>();

        String sql = "SELECT isbn, title, author, published_date, publisher, description, quantity FROM books";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Books book = new Books();
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

    public void addBook(Books book) throws SQLException {
        String sql = "INSERT INTO books(isbn, title, author, published_date, publisher, description, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

            stmt.executeUpdate();
        }
    }
}
