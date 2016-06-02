package com.targa.dev.formation.shiroj.security.control;

import com.targa.dev.formation.shiroj.security.configuration.BCryptPasswordService;
import com.targa.dev.formation.shiroj.security.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by nebrass on 17/11/2015.
 */
@Stateless
public class UserService {
    @PersistenceContext
    EntityManager em;

    @Inject
    BCryptPasswordService passwordService;

    @Inject
    private Logger logger;

    public User save(User user) {
        return this.em.merge(user);
    }

    public User findById(long id) {
        return this.em.find(User.class, id);
    }

    public List<User> findAll() {
        return this.em
                .createNamedQuery("User.findAll", User.class)
                .getResultList();
    }

    public User findByUsername(String username) {
        User result = null;
        try {
            result = this.em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.info("UserService : No valid User was found for [" + username + "] : " + e);
        } finally {
            return result;
        }
    }

    public void delete(long id) {
        User userReference;
        try {
            userReference = this.em.getReference(User.class, id);
            this.em.remove(userReference);
        } catch (EntityNotFoundException e) {
            logger.finest("User with id[" + id + "] was not delete because it doesn't exist." + e);
        }
    }
}

