package org.acme.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entities.Reservation;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReservationRepository {
    @Inject
    DataSource dataSource;

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT * FROM reservations";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = this.getReservationFromResultSet(rs);
                reservations.add(reservation);
            }
        }

        return reservations;
    }


    public Reservation saveReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (user_id, book_id, date) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, reservation.getUserId());
            stmt.setLong(2, reservation.getBookId());
            stmt.setDate(3, Date.valueOf(reservation.getDate()));

            stmt.executeUpdate();
        }

        return reservation;
    }

    public void deleteReservation(Long id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Reservation getReservationFromResultSet(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getLong("id"));
        reservation.setUserId(rs.getLong("user_id"));
        reservation.setBookId(rs.getLong("book_id"));
        reservation.setDate(rs.getDate("date").toLocalDate());

        return reservation;
    }
}
