package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entities.Books;
import org.acme.services.BookService;

import java.sql.SQLException;
import java.util.List;

@Path("/books")
public class BookController {
    @Inject
    BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        try {
            List<Books> books = bookService.getBooks();
            return Response.ok(books).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error when fetching books")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Books book) {
        try {
            bookService.addBook(book);
            return Response.status(Response.Status.CREATED).entity(book).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when adding the book" + e.getMessage())
                    .build();
        }
    }
}
