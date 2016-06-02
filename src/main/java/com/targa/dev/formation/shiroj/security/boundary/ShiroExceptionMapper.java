package com.targa.dev.formation.shiroj.security.boundary;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by nebrass on 18/11/2015.
 */
@Provider
public class ShiroExceptionMapper implements ExceptionMapper<Exception> {

    private static final String CAUSE = "cause";

    @Override
    public Response toResponse(Exception ex) {

        if (ex instanceof UnknownAccountException) {
            return Response.status(Response.Status.FORBIDDEN)
                    .header(ShiroExceptionMapper.CAUSE, "Your username wrong")
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
        if (ex instanceof IncorrectCredentialsException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .header(ShiroExceptionMapper.CAUSE, "Password is incorrect")
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
        if (ex instanceof LockedAccountException) {
            return Response.status(Response.Status.CONFLICT)
                    .header(ShiroExceptionMapper.CAUSE, "This username is locked")
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
        if (ex instanceof AuthenticationException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header(ShiroExceptionMapper.CAUSE, ex.getMessage())
                    .type(MediaType.TEXT_HTML)
                    .build();
        }

        return Response.serverError().
                header(ShiroExceptionMapper.CAUSE, ex.toString()).build();
    }
}
