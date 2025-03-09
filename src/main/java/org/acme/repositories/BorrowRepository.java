package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Borrow;
import javax.sql.DataSource;
import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BorrowRepository {
    @Inject
    private DataSource dataSource;

    public List<Borrow> getAllBorrows() throws SQLException {
        List<Borrow> borrows = new ArrayList<>();

        String sql = "SELECT id, user_id, book_id, \"date\", due_date, return_date FROM borrows";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Borrow borrow = this.getBorrowFromResultSet(rs);
                borrows.add(borrow);
            }
        }

        return borrows;
    }

    public Borrow getBorrowById(Long id) throws SQLException {
        String sql = "SELECT id, user_id, book_id, \"date\", due_date, return_date FROM borrows " +
                     "WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return this.getBorrowFromResultSet(rs);
            }
        }
        return null;
    }

    public Borrow saveBorrow(Borrow borrow) throws SQLException {
        String sql = "INSERT INTO borrows (user_id, book_id, date, due_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, borrow.getUserId());
            stmt.setLong(2, borrow.getBookId());
            stmt.setDate(3, Date.valueOf(borrow.getDate()));
            stmt.setDate(4, Date.valueOf(borrow.getDueDate()));

            stmt.executeUpdate();
        }

        return borrow;
    }

    public void deleteBorrow(Long id) throws SQLException {
        String sql = "DELETE FROM borrows WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }

    }

    public Borrow getBorrowFromResultSet(ResultSet rs) throws SQLException {
        Borrow borrow = new Borrow();
        borrow.setId(rs.getLong("id"));
        borrow.setUserId(rs.getLong("user_id"));
        borrow.setBookId(rs.getLong("book_id"));
        borrow.setDate(rs.getDate("date").toLocalDate());
        borrow.setDueDate(rs.getDate("due_date").toLocalDate());
        if (rs.getDate("return_date") != null) {
            borrow.setReturnDate(rs.getDate("return_date").toLocalDate());
        } else {
            borrow.setReturnDate(null);
        }
        return borrow;
    }




}
