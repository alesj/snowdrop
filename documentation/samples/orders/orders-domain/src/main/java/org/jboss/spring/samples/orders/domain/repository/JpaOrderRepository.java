package org.jboss.spring.samples.orders.domain.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.jboss.spring.samples.orders.domain.entities.Order;
import org.jboss.spring.samples.orders.domain.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: marius
 */
@Repository
public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Order> getOrdersForUser(User user) {
        Query q = entityManager.createQuery("select o from Order o where o.createdBy.id = ?1");
        q.setParameter(1, user.getId());
        return (List<Order>) q.getResultList();
    }

}
