package springdemo.business;

import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.IOException;

@Service
public class HelloServiceImpl implements HelloService {

    public String greet(String name) {
        return "Hello, " + name + "!";
    }

}
