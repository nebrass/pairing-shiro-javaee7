package com.targa.dev.formation.shiroj.security;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nebrass Lamouchi <lnibrass at gmail.com>
 */
@Provider
@PreMatching
public class RESTCorsDemoResponseFilter implements ContainerResponseFilter {

    private final static Logger log = Logger.getLogger(RESTCorsDemoResponseFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        log.info("Executing REST response filter");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

    }
}
