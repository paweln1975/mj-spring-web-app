package pl.paweln.mjspringwebapp.config;

public class JokeServiceFactory {
    public JokeServiceProvider createJokeServiceProvider() {
        return new JokeServiceProvider();
    }
}
