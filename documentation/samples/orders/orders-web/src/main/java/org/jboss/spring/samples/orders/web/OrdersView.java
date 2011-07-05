package org.jboss.spring.samples.orders.web;

import java.util.List;

import org.jboss.spring.samples.orders.domain.entities.Order;
import org.jboss.spring.samples.orders.domain.ejb.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.EJB;


/**
 * @author: marius
 */
@Controller
@RequestMapping("/orders/*")
public class OrdersView {

    @EJB(mappedName = "orders-ear-1.0.0-SNAPSHOT/OrderServiceBean/local")
    private OrderService orderService;

    @RequestMapping("created-by")
    @Transactional
    public
    @ModelAttribute("orders")
    List<Order> getOrdersCreatedBy(@RequestParam("user") String userId) {
        return orderService.getOrdersForUserId(userId);
    }

}
