package com.targa.dev.formation.shiroj.security.boundary;

import com.targa.dev.formation.shiroj.security.configuration.WebPages;
import com.targa.dev.formation.shiroj.security.control.AuthenticationEventMonitor;
import com.targa.dev.formation.shiroj.security.control.UserService;
import com.targa.dev.formation.shiroj.security.entity.AuthenticationEvent;
import com.targa.dev.formation.shiroj.security.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by nebrass on 03/01/2016.
 */
@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @Inject
    private Logger logger;

    @Inject
    private UserService userService;

    @Inject
    private Event<AuthenticationEvent> monitoring;

    @Inject
    private AuthenticationEventMonitor authenticationEventMonitor;


    @POST
    @Path("login")
    public Response login(@NotNull @FormParam("username") String username,
                          @NotNull @FormParam("password") String password,
                          @NotNull @FormParam("rememberMe") boolean rememberMe,
                          @Context HttpServletRequest request) {

        boolean justLogged = SecurityUtils.getSubject().isAuthenticated();

        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, rememberMe));
        } catch (Exception e) {
            throw new IncorrectCredentialsException("Unknown user, please try again");
        }

        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        monitoring.fire(new AuthenticationEvent(username, AuthenticationEvent.Type.LOGIN));
        if (savedRequest != null) {
            return this.getRedirectResponse(savedRequest.getRequestUrl(), request);
        } else {
            if (justLogged) {
                return this.getRedirectResponse(WebPages.DASHBOARD_URL, request);
            }
            return this.getRedirectResponse(WebPages.HOME_URL, request);
        }
    }

    @GET
    @Path("logout")
    public Response logout(@Context HttpServletRequest request) {
        SecurityUtils.getSubject().logout();

        return this.getRedirectResponse(WebPages.HOME_URL, request);
    }


    @GET
    @Path("me")
    public Response getSubjectInfo() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated()) {
            User connectedUser = this.userService.findByUsername(subject.getPrincipal().toString());
            return Response.ok(connectedUser).build();
        } else {
            return Response.serverError()
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
    }

    @GET
    @Path("users")
    public Response getConnectedUsers() {
        List<String> connectedUsers = this.authenticationEventMonitor.getConnectedUsers();
        return Response.ok(connectedUsers).build();
    }


    private Response getRedirectResponse(String requestedPath, HttpServletRequest request) {
        String appName = request.getContextPath();

        String baseUrl = request.getRequestURL().toString().split(request.getRequestURI())[0] + appName;

        try {
            if (requestedPath.split(appName).length > 1) {
                baseUrl += requestedPath.split(appName)[1];
            } else {
                baseUrl += requestedPath;
            }

            return Response.seeOther(new URI(baseUrl)).build();
        } catch (URISyntaxException ex) {
            logger.log(Level.WARNING, "URL redirection failed for request[{0}] : {1}", new Object[]{request, ex});
            return Response.serverError().status(404)
                    .type(MediaType.TEXT_HTML).build();
        }
    }

}