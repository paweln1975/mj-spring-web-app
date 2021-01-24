package pl.paweln.mjspringwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.paweln.mjspringwebapp.beans.JmsManager;
import pl.paweln.mjspringwebapp.beans.OptionManager;
import pl.paweln.mjspringwebapp.controllers.DIController;
import pl.paweln.mjspringwebapp.controllers.I18nController;
import pl.paweln.mjspringwebapp.controllers.exercises.PetController;
import pl.paweln.mjspringwebapp.services.JokeService;
import pl.paweln.mjspringwebapp.services.JokeServiceImpl;

@SpringBootApplication
@ComponentScan(basePackages = {"pl.paweln.mjspringwebapp"})

public class DemoSpringWebAppApplication {
	public static void main(String[] args) {
		ApplicationContext ctx =
		 SpringApplication.run(DemoSpringWebAppApplication.class, args);

//		for (String s : ctx.getBeanDefinitionNames()) {
//			System.out.println(s);
//		}

		I18nController i18nController = (I18nController) ctx.getBean("i18nController");
		System.out.println(i18nController.sayHello());

		DIController controller = (DIController) ctx.getBean("diController");
		System.out.println(controller.sayHello());
		System.out.println(controller.sayHelloWorld());
		System.out.println(controller.sayHelloPrimary());

		PetController petController = ctx.getBean("petController", PetController.class);
		System.out.println("--- The Best Pet is ---");
		System.out.println(petController.whichPetIsTheBest());

		JokeService jokeService = ctx.getBean("jokeServiceImpl", JokeServiceImpl.class);
		System.out.println(jokeService.getJoke());

		// configuration from property file
		OptionManager optionManager = ctx.getBean(OptionManager.class);
		System.out.println("Option 1=" + optionManager.getOption1());
		System.out.println("Option 2=" + optionManager.getOption2());
		System.out.println("JAVA_HOME=" + optionManager.getJavaHome());

		JmsManager jmsManager = ctx.getBean(JmsManager.class);
		System.out.println("Username:" + jmsManager.getUserName());
		System.out.println("Password:" + jmsManager.getPassword());
	}
}
