package springdemo.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import springdemo.business.HelloService;

import java.util.Map;
import java.util.HashMap;

@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public Map sayHello(@RequestParam("name") String name) {
        Map model = new HashMap();
        model.put("message", helloService.greet(name));
        return model;
    }

}
