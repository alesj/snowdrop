package org.jboss.spring.samples.orders.domain.ejb;

import org.jboss.spring.samples.orders.domain.repository.UserRepository;
import org.jboss.spring.samples.orders.domain.entities.User;

import javax.ejb.Local;

/**
 * @author: marius
 */
@Local
public interface UserService {

    User createUser(String id);

}
