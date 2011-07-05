package org.jboss.spring.samples.orders.domain.ejb;

import org.jboss.spring.vfs.context.VFSClassPathXmlApplicationContext;
import org.jboss.spring.samples.orders.domain.repository.UserRepository;
import org.jboss.spring.samples.orders.domain.repository.OrderRepository;
import org.jboss.spring.samples.orders.domain.entities.Order;
import org.jboss.spring.samples.orders.domain.entities.User;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * @author: marius
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class OrderServiceBean implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderServiceBean() {
//      VFSClassPathXmlApplicationContext applicationContext =
//            new VFSClassPathXmlApplicationContext(
//                  new String[]{"classpath:/application-context.xml",
//                        "classpath:/application-infrastructure.xml"});
//
//      userRepository = ((UserRepository) applicationContext.getBeansOfType(UserRepository.class).values().iterator().next());
//      orderRepository = ((OrderRepository) applicationContext.getBeansOfType(OrderRepository.class).values().iterator().next());
    }

    public List<Order> getOrdersForUserId(String userId) {
        User u = userRepository.getUserById(userId);
        return orderRepository.getOrdersForUser(u);
    }
}
