package com.targa.dev.formation.shiroj.security.boundary;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nebrass on 17/11/2015.
 */
@WebFilter(displayName = "NoCacheFilter",
        urlPatterns = {"/*"},
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC,
                DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST},
        asyncSupported = true
)
public class NoCacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Init method is useles
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setDateHeader("Expires", 0); // Proxies.
        chain.doFilter(req, res);

    }

    @Override
    public void destroy() {
        // Destroy method is useles
    }

}
