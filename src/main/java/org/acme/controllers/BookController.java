package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entities.Book;
import org.acme.services.BookService;

import java.sql.SQLException;
import java.util.List;

@Path("/book")
public class BookController {
    @Inject
    BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        try {
            List<Book> books = bookService.getBooks();
            return Response.ok(books).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error when fetching books")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") long id) {
        try {
            Book book = bookService.getBookById(id);
            if (book != null) {
                return Response.ok(book).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Book not found")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when fetching the book: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooks(List<Book> books) {
        try {
            bookService.addBooks(books);
            return Response.status(Response.Status.CREATED).entity(books).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when adding the books: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") long id, Book book) {
        try {
            bookService.updateBook(id, book);
            return Response.ok(book).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when updating the book: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") long id) {
        try {
            bookService.deleteBook(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when deleting the book: " + e.getMessage())
                    .build();
        }
    }
}
