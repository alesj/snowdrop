package springdemo.weaving.web;

import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import springdemo.weaving.hello.HelloService;

@Controller
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping("/hello")
    public Map sayHello(String name) {
        Map map = new HashMap();
        map.put("message", helloService.sayHello(name));
        return map;
    }
}
