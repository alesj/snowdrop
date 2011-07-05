package org.jboss.spring.samples.orders.domain.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.spring.samples.orders.domain.entities.User;
import org.springframework.stereotype.Repository;

/**
 * @author: marius
 */
@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public User getUserById(String id) {
        return entityManager.find(User.class, id);
    }

    public User createUser(String id) {
        User u = new User(id);
        entityManager.persist(u);
        return u;
    }
}
