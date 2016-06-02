package com.targa.dev.formation.shiroj.security.configuration;

import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.annotation.WebFilter;

/**
 * Created by nebrass on 17/11/2015.
 */
@WebFilter("/*")
public class ShiroFilterActivator extends ShiroFilter {

    private ShiroFilterActivator() {
    }
}
