package com.targa.dev.formation.shiroj.security.control;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by nebrass on 17/11/2015.
 */
public class BCryptCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        if (token instanceof UsernamePasswordToken) {
            String password = toString(((UsernamePasswordToken) token).getPassword());
            String hashedPassword = getCredentials(info);

            return BCrypt.checkpw(password, hashedPassword);
        } else {
            throw new ShiroException("You aren't passing in passwords");
        }
    }

    private String getCredentials(AuthenticationInfo info) {

        Object credentials = info.getCredentials();
        return toString(credentials);
    }

    private String toString(Object o) {
        if (o == null) {
            String msg = "Argument for String conversion cannot be null.";
            throw new IllegalArgumentException(msg);
        }
        if (o instanceof byte[]) {
            return toString((byte[]) o);
        } else if (o instanceof char[]) {
            return new String((char[]) o);
        } else if (o instanceof String) {
            return (String) o;
        } else {
            return o.toString();
            
        }
    }
}

