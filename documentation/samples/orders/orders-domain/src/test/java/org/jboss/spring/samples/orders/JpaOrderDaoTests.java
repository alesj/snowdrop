package org.jboss.spring.samples.orders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import org.jboss.spring.samples.orders.domain.entities.Order;
import org.jboss.spring.samples.orders.domain.entities.User;
import org.jboss.spring.samples.orders.domain.repository.OrderRepository;
import org.jboss.spring.samples.orders.domain.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: marius
 */
@ContextConfiguration(locations = {"classpath:application-context.xml", "classpath:test-infrastructure.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class JpaOrderDaoTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    EntityManager manager;

    @Before
    @Transactional
    public void setUp() {
        User u = userRepository.getUserById("user1");
        if (u == null) {
            User newUser = new User("user1");
            manager.persist(newUser);
            Order o = new Order();
            o.setCreatedBy(newUser);
            o.setName("A new order");
            manager.persist(o);
        }
    }

    @Test
    @Transactional
    public void testGetOrders() {
        User u = userRepository.getUserById("user1");
        Assert.assertNotNull(u);
        List<Order> orders = orderRepository.getOrdersForUser(u);
        Assert.assertTrue(orders.size() > 0);
    }
}
