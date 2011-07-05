package springdemo.weaving.hello;

import org.springframework.stereotype.Component;

@Component
public class HelloService {

    public String sayHello(String name) {
        return doInternal(name);
    }

    private String doInternal(String name) {
        return "Hello, " + name + "!";
    }

}
