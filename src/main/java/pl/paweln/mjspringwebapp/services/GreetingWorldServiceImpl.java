package pl.paweln.mjspringwebapp.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
public class GreetingWorldServiceImpl implements GreetingService {
    @Override
    public String sayHello() {
        return "Hello World!";
    }
}
