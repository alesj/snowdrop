package org.jboss.spring.samples.orders.domain.repository;

import java.util.List;

import org.jboss.spring.samples.orders.domain.entities.Order;
import org.jboss.spring.samples.orders.domain.entities.User;

/**
 * @author: marius
 */
public interface OrderRepository {

    List<Order> getOrdersForUser(User user);

}
