package pl.paweln.mjspringwebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class JokeConfiguration {
    @Bean
    @Profile("de")
    public JokeServiceProvider getJokeService() {
        System.out.println("Using spring configuration class to provide JokeServiceProvider");
        return new JokeServiceProvider();
    }

    @Bean
    public JokeServiceFactory getJokeServiceFactory() {
        return new JokeServiceFactory();
    }

    @Bean
    @Primary
    @Profile({"default", "en"})
    public JokeServiceProvider getJokeServicesPrimary(JokeServiceFactory jokeServiceFactory) {
        System.out.println("Using spring configuration class to provide JokeServiceFactory and JokeServiceProvider");
        return jokeServiceFactory.createJokeServiceProvider();
    }
}
