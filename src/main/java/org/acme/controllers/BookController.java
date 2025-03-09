package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.BookDTO;
import org.acme.entities.Book;
import org.acme.services.BookService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/books")
public class BookController {
    @Inject
    BookService bookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        try {
            List<Book> books = bookService.getBooks();
            List<BookDTO> response = books.stream().map(book -> BookDTO.fromBook(book)).toList();
            return Response.ok(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error when fetching books")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") Long id) {
        try {
            Book book = bookService.getBookById(id);
            BookDTO response = BookDTO.fromBook(book);
            if (book != null) {
                return Response.ok(response).build();
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
    public Response saveBook(BookDTO bookDTO) {
        try {
            Long newBookId = bookService.saveBook(bookDTO);
            if (newBookId == null) {
                throw new SQLException();
            }
            BookDTO response = BookDTO.fromBook(bookService.getBookById(newBookId));
            return Response.status(Response.Status.CREATED).entity(response).build();

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
    public Response updateBook(@PathParam("id") Long id, BookDTO bookDTO) {
        try {
            bookService.updateBook(id, bookDTO);
            BookDTO response = BookDTO.fromBook(bookService.getBookById(id));
            return Response.ok(response).build();
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
