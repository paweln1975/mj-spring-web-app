package pl.paweln.mjspringwebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import pl.paweln.mjspringwebapp.beans.JmsManager;
import pl.paweln.mjspringwebapp.beans.OptionManager;

@Configuration
@PropertySources({
        @PropertySource("classpath:config.properties"),
        @PropertySource("classpath:jms.properties")
})

public class OptionPropertyConfig {

    @Autowired
    Environment environment;

    @Value("${property.option1}")
    String option1;

    @Value("${property.option2}")
    String option2;

    @Value("${jms.username}")
    String username;

    @Value("${jms.password}")
    String password;

    @Bean
    public JmsManager jmsManager() {
        JmsManager manager = new JmsManager();
        manager.setPassword(password);
        manager.setUserName(username);
        return manager;
    }

    @Bean
    public OptionManager optionManager() {
        OptionManager optionManager = new OptionManager();
        optionManager.setOption1(option1);
        optionManager.setOption2(option2);
        optionManager.setJavaHome(environment.getProperty("JAVA_HOME"));
        return optionManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
        return conf;
    }
}
