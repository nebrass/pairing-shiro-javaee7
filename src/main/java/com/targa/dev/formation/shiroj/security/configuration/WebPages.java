package com.targa.dev.formation.shiroj.security.configuration;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * Created by nebrass on 20/11/2015.
 */
public final class WebPages {
    public static final String LOGIN_URL = "/login.html";
    public static final String HOME_URL = "/login.html";
    public static final String DASHBOARD_URL = "/account/";

    private WebPages() {
    }

    @Produces
    public Logger produceLogger(InjectionPoint ip) {
        return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
    }
}
