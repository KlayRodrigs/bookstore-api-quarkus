package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entities.UserEntity;
import org.acme.services.UserService;

import java.sql.SQLException;
import java.util.List;

@Path("/user")
public class UserController {
    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        try {
            List<UserEntity> userEntities = userService.getUsers();
            return Response.ok(userEntities).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error when fetching users")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") long id) {
        try {
            UserEntity userEntity = userService.getUserById(id);
            if (userEntity != null) {
                return Response.ok(userEntity).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found")
                        .build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when fetching the user: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUsers(List<UserEntity> userEntities) {
        try {
            userService.addUsers(userEntities);
            return Response.status(Response.Status.CREATED).entity(userEntities).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when adding the users: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") long id, UserEntity userEntity) {
        try {
            userService.updateUser(id, userEntity);
            return Response.ok(userEntity).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when updating the user: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") long id) {
        try {
            userService.deleteUser(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error when deleting the user: " + e.getMessage())
                    .build();
        }
    }
}