package org.acme.dtos;

import org.acme.entities.Book;
import org.acme.entities.Reservation;
import org.acme.entities.User;

import java.time.LocalDate;

public record ReservationResponseDTO(Long id, User user, Book book, LocalDate date) {
    public static ReservationResponseDTO fromReservation(User user, Book book, Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                user,
                book,
                reservation.getDate()
        );
    }
}
