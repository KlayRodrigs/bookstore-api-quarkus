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

    public int countBooks() throws SQLException {
        String sql = "SELECT COUNT(*) FROM books";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Book getBookById(Long id) throws SQLException {
        String sql = "SELECT id, isbn, title, author, published_date, publisher, description, category, image_url " +
                     "FROM books " +
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

    public Book getBookByIsbn(String isbn) throws SQLException {
        String sql = "SELECT id, isbn, title, author, published_date, publisher, description, category, image_url " +
                     "FROM books " +
                     "WHERE isbn = ?";

        Book book = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, isbn);
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

        String sql = "SELECT id, isbn, title, author, published_date, publisher, description, category, image_url " +
                     "FROM books";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(this.getBookFromResultSet(rs));
            }
        }

        return books;
    }

    public Long saveBook(BookDTO bookDTO) throws SQLException {
        String sql = "INSERT INTO books (isbn, title, author, published_date, publisher, description, category, image_url) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {

            var stmt = this.createBook(bookDTO, connection, sql);
            int insertedRow = stmt.executeUpdate();
            if (insertedRow > 0) {
                var rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return id;
                }
            }
        }
        return null;
    }

    public void updateBook(Long id, BookDTO bookDTO) throws SQLException {
        String sql = "UPDATE books " +
                     "SET isbn = ?, title = ?, author = ?, published_date = ?, publisher = ?, description = ?, category = ?, image_url = ? " +
                     "WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {

            var stmt = this.createBook(bookDTO, connection, sql);

            stmt.setLong(9, id);
            stmt.executeUpdate();
        }
    }

    public void deleteBook(Long id) throws SQLException {
        String sql = "DELETE FROM books " +
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
        book.setId(rs.getLong("id"));
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPublishedDate(rs.getDate("published_date"));
        book.setPublisher(rs.getString("publisher"));
        book.setDescription(rs.getString("description"));
        book.setCategory(rs.getString("category"));
        book.setImageUrl(rs.getString("image_url"));

        return book;
    }

    private PreparedStatement createBook(BookDTO bookDTO, Connection connection, String sql) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, bookDTO.isbn());
        stmt.setString(2, bookDTO.title());
        stmt.setString(3, bookDTO.author());
        stmt.setDate(4, bookDTO.publishedDate());
        stmt.setString(5, bookDTO.publisher());
        stmt.setString(6, bookDTO.description());
        stmt.setString(7, bookDTO.category());
        stmt.setString(8, bookDTO.imageUrl());

        return stmt;
    }
}
