package com.targa.dev.formation.shiroj.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by nebrass on 25/12/2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
    @NotNull
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @NotNull
    @Past
    @Column(name = "creation", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    public Role() {
        this.enabled = true;
        this.creation = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final Role other = (Role) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role{" + "name=" + name + '}';
    }
}
