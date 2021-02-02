package pl.paweln.mjspringwebapp.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"default", "jpa"})
@Service("i18nService")
public class I18nEnglishGreetingService implements GreetingService {
    @Override
    public String sayHello() {
        return "Hello World - EN";
    }
}
