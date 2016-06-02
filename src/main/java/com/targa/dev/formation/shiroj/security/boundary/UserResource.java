package com.targa.dev.formation.shiroj.security.boundary;

import com.targa.dev.formation.shiroj.security.control.UserService;
import com.targa.dev.formation.shiroj.security.entity.User;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by nebrass on 03/01/2016.
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    public Response addUser(@Valid User user, @Context UriInfo info) {
        User saved = this.userService.save(user);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") long id) {
        this.userService.delete(id);
        return Response.ok().build();

    }

    @PUT
    public Response editUser(@Valid User user, @Context UriInfo info) {
        User searched = this.userService.save(user);
        return Response.ok(searched).build();

    }

    @GET
    @Path("{id}")
    public Response findUser(@PathParam("id") long id) {
        User searched = this.userService.findById(id);
        return Response.ok(searched).build();

    }

    @GET
    public Response listUsers() {
        List<User> all = this.userService.findAll();
        return Response.ok(all).build();

    }
}
