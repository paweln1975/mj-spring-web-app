package pl.paweln.mjspringwebapp.services;

import org.springframework.stereotype.Service;
import pl.paweln.mjspringwebapp.config.JokeServiceProvider;

@Service
public class JokeServiceImpl implements JokeService {

    private JokeServiceProvider jokeServiceProvider;

    public JokeServiceImpl(JokeServiceProvider jokeServiceProvider) {
        this.jokeServiceProvider = jokeServiceProvider;
    }

    @Override
    public String getJoke() {
        return jokeServiceProvider.getJoke();
    }
}
