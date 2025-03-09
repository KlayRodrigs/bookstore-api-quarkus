package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Borrow;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ApplicationScoped
public class BorrowRepository {
    @Inject
    private DataSource dataSource;


    public Borrow saveBorrow(Borrow borrow) throws SQLException {
        String sql = "INSERT INTO borrow (user_id, book_id, data, due_date) VALUES (?, ?, ?, ?)";

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




}
