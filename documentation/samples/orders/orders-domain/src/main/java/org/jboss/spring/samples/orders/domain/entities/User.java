package org.jboss.spring.samples.orders.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: marius
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String id;

    public User(String id) {
        this.id = id;
    }

    protected User() {
        // no-arg constructor for persistence
    }

    public String getId() {
        return id;
    }
}
