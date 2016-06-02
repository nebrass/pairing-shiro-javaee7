package com.targa.dev.formation.shiroj.security.configuration;

import com.targa.dev.formation.shiroj.security.control.UserService;
import com.targa.dev.formation.shiroj.security.entity.Role;
import com.targa.dev.formation.shiroj.security.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by nebrass on 25/12/2015.
 */
@Singleton
@Startup
public class InsertUser {
    @Inject
    UserService us;
    @Inject
    BCryptPasswordService passwordService;

    private Logger LOGGER = Logger.getLogger(InsertUser.class.getName());

    @PostConstruct
    public void insert() {
        User ahmed = new User("ahmed", passwordService.encryptPassword("hello"));
        Role role = new Role();
        role.setName("ADMIN");
        ahmed.setRole(role);

        this.us.save(ahmed);
        LOGGER.warning("######################");
    }
}
