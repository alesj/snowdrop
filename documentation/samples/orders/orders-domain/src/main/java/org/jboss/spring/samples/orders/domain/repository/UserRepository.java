package org.jboss.spring.samples.orders.domain.repository;

import org.jboss.spring.samples.orders.domain.entities.User;

/**
 * @author: marius
 */
public interface UserRepository {

    User getUserById(String id);

    User createUser(String id);
}
