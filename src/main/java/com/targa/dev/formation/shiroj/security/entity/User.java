package com.targa.dev.formation.shiroj.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by nebrass on 17/11/2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
    @NotNull
    @Column(name = "username", length = 50, nullable = false)
    private String username;
    @NotNull
    @XmlTransient
    @Column(name = "password", nullable = false)
    private String password;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Role role;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @Version
    @Column(name = "version", nullable = false)
    private Timestamp version;

    public User() {
        this("", "", Boolean.TRUE);
    }

    public User(String username, String password) {
        this(username, password, Boolean.TRUE);
    }

    public User(String username, String password, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the version
     */
    public Timestamp getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Timestamp version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (this.getId() ^ (this.getId() >>> 32));
        hash = 67 * hash + Objects.hashCode(this.getUsername());
        hash = 67 * hash + Objects.hashCode(this.getPassword());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + getId() + ", username=" + getUsername() + ", password=" + getPassword() + ", enabled=" + getEnabled() + ", role=" + getRole().getName() + "}";
    }

}
