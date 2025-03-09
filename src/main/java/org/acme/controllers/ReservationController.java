package org.acme.controllers;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.ReservationDTO;
import org.acme.dtos.ReservationResponseDTO;
import org.acme.entities.Book;
import org.acme.entities.Reservation;
import org.acme.entities.User;
import org.acme.services.BookService;
import org.acme.services.ReservationService;
import org.acme.services.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/reservations")
public class ReservationController {

    @Inject
    private ReservationService reservationService;

    @Inject
    private UserService userService;

    @Inject
    private BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            List<ReservationResponseDTO> response = new ArrayList<>();

            for (Reservation reservation : reservations) {
                User user = userService.getUserById(reservation.getUserId());
                Book book = bookService.getBookById(reservation.getBookId());
                response.add(ReservationResponseDTO.fromReservation(user, book, reservation));
            }

            return Response.status(Response.Status.OK).entity(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when get all reservations")
                    .build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(ReservationDTO dto) {
        try {
            Reservation newReservation = reservationService.saveReservation(dto);
            User user = userService.getUserById(newReservation.getUserId());
            Book book = bookService.getBookById(newReservation.getBookId());
            ReservationResponseDTO response = ReservationResponseDTO.fromReservation(user, book, newReservation);

            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when create reservation")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservation(@PathParam("id") Long id) throws SQLException {
        reservationService.deleteReservation(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
