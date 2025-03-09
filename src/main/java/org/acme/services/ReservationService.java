package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dtos.ReservationDTO;
import org.acme.entities.Book;
import org.acme.entities.Reservation;
import org.acme.entities.User;
import org.acme.repositories.ReservationRepository;

import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;

@ApplicationScoped
public class ReservationService {
    @Inject
    ReservationRepository reservationRepository;

    @Inject
    UserService userService;

    @Inject
    BookService bookService;

    public List<Reservation> getAllReservations() throws SQLException {
        return reservationRepository.getAllReservations();
    }

    public Reservation saveReservation(ReservationDTO reservationDTO) throws SQLException {
        User user = userService.getUserByCpf(reservationDTO.cpf()).orElseThrow(SQLException::new);;
        Book book = bookService.getBookByIsbn(reservationDTO.isbn()).orElseThrow(SQLException::new);;

        Reservation newReservation = new Reservation();
        newReservation.setUserId(user.getId());
        newReservation.setBookId(book.getId());
        newReservation.setDate(LocalDate.now());

        return reservationRepository.saveReservation(newReservation);
    }

    public void deleteReservation(Long id) throws SQLException {
        reservationRepository.deleteReservation(id);
    }

}
