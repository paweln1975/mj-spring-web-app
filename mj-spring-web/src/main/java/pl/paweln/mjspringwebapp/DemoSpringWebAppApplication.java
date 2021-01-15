package pl.paweln.mjspringwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.paweln.mjspringwebapp.controllers.DIController;
import pl.paweln.mjspringwebapp.controllers.I18nController;
import pl.paweln.mjspringwebapp.controllers.exercises.PetController;

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
	}
}
