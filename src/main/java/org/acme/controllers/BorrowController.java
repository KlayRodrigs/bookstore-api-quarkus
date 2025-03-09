package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.BorrowDTO;
import org.acme.dtos.BorrowResponseDTO;
import org.acme.entities.Book;
import org.acme.entities.Borrow;
import org.acme.entities.User;
import org.acme.services.BookService;
import org.acme.services.BorrowService;
import org.acme.services.UserService;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/borrows")
public class BorrowController {
    @Inject
    BorrowService borrowService;

    @Inject
    UserService userService;

    @Inject
    BookService bookService;

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countBorrow() {
        try {
            var borrowCount = userService.countUsers();
            var response = new HashMap<>();
            response.put("borrowCount", borrowCount);
            return Response.ok(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error when counting borrows")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBorrows() {
        try {
            List<Borrow> borrows = borrowService.getAllBorrows();
            List<BorrowResponseDTO> response = new ArrayList<>();

            for (Borrow borrow : borrows) {
                User user = userService.getUserById(borrow.getUserId());
                Book book = bookService.getBookById(borrow.getBookId());
                response.add(BorrowResponseDTO.mapToResponse(user, book, borrow));
            }

            return Response.status(Response.Status.OK).entity(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when get borrows: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getBorrowById(@PathParam("id") Long id) {
        try {
            Borrow borrow = borrowService.getBorrowById(id);
            User user = userService.getUserById(borrow.getUserId());
            Book book = bookService.getBookById(borrow.getBookId());
            BorrowResponseDTO response = BorrowResponseDTO.mapToResponse(user, book, borrow);
            if (borrow != null) {
                return Response.status(Response.Status.OK).entity(response).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when get the borrow: " + e.getMessage())
                    .build();
        }
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBorrow(BorrowDTO dto) {
        try {
            Borrow borrow = borrowService.createBorrow(dto);
            User user = userService.getUserById(borrow.getUserId());
            Book book = bookService.getBookById(borrow.getBookId());
            BorrowResponseDTO response = BorrowResponseDTO.mapToResponse(user, book, borrow);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity("Error when adding the borrow: " + e.getMessage())
                   .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBorrow(@PathParam("id") Long id) throws SQLException {
        borrowService.deleteBorrow(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


}
