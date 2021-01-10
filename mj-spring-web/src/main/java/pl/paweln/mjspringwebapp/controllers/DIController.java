package pl.paweln.mjspringwebapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import pl.paweln.mjspringwebapp.services.GreetingService;
import pl.paweln.mjspringwebapp.services.GreetingServiceImpl;
import pl.paweln.mjspringwebapp.services.GreetingWorldServiceImpl;

@Controller("diController")
public class DIController {

    private final GreetingService greetingService;

    private GreetingService greetingWorldService;

    @Autowired
    private GreetingService greetingServicePrimary;

    public DIController(@Qualifier(value = "greetingServiceImpl") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello() {
        return this.greetingService.sayHello();
    }

    public String sayHelloWorld() {
        return this.greetingWorldService.sayHello();
    }

    public String sayHelloPrimary() {
        return this.greetingServicePrimary.sayHello();
    }

    @Autowired
    @Qualifier("greetingWorldServiceImpl")
    public void setGreetingWorldService(GreetingService greetingWorldService) {
        this.greetingWorldService = greetingWorldService;
    }
}
