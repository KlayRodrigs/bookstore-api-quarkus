package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.BorrowDTO;
import org.acme.services.BorrowService;

import java.sql.SQLException;

@Path("/borrows")
public class BorrowController {
    @Inject
    BorrowService borrowService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBorrow(BorrowDTO dto) {
        try {
            borrowService.createBorrow(dto);
            return Response.status(Response.Status.CREATED).entity(dto).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity("Error when adding the borrow: " + e.getMessage())
                   .build();
        }
    }



}
