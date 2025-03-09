package org.acme.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dtos.UserDTO;
import org.acme.entities.User;
import org.acme.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Path("/users")
public class UserController {
    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        try {
            List<User> userList = userService.getUsers();
            List<UserDTO> response = userList.stream().map(user -> UserDTO.fromUser(user)).toList();
            return Response.ok(response).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Error when fetching users")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        try {
            User user = userService.getUserById(id);

            if (user != null) {
                UserDTO response = UserDTO.fromUser(user);
                return Response.ok(response).build();
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
    public Response saveUser(UserDTO userDTO) {
        try {
            Long newUserId = userService.saveUser(userDTO);
            if (newUserId == null) {
                throw new SQLException();
            }
            UserDTO response = UserDTO.fromUser(userService.getUserById(newUserId));
            return Response.status(Response.Status.CREATED).entity(response).build();
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
    public Response updateUser(@PathParam("id") Long id, UserDTO userDTO) {
        try {
            userService.updateUser(id, userDTO);
            UserDTO response = UserDTO.fromUser(userService.getUserById(id));
            return Response.ok(response).build();
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