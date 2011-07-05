package org.jboss.spring.samples.orders.domain.ejb;

import org.jboss.spring.samples.orders.domain.entities.Order;

import javax.ejb.Local;
import java.util.List;

/**
 * @author: marius
 */
@Local
public interface OrderService {

    List<Order> getOrdersForUserId(String userId);

}
