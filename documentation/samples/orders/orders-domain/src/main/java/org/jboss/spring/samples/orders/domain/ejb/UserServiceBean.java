package org.jboss.spring.samples.orders.domain.ejb;

import org.jboss.spring.samples.orders.domain.repository.UserRepository;
import org.jboss.spring.samples.orders.domain.entities.User;
import org.jboss.spring.vfs.context.VFSClassPathXmlApplicationContext;

import javax.ejb.Stateless;

/**
 * @author: marius
 */
@Stateless
public class UserServiceBean implements UserService {

    private UserRepository userRepository;

    public UserServiceBean() {
        VFSClassPathXmlApplicationContext applicationContext =
                new VFSClassPathXmlApplicationContext(
                        new String[]{"classpath:/application-context.xml",
                                "classpath:/application-infrastructure.xml"});

        userRepository = ((UserRepository) applicationContext.getBeansOfType(UserRepository.class).values().iterator().next());
    }

    public User createUser(String id) {
        return userRepository.createUser(id);
    }

}
