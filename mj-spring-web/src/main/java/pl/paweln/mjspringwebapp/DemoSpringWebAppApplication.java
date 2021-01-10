package pl.paweln.mjspringwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.paweln.mjspringwebapp.controllers.DIController;

@SpringBootApplication
public class DemoSpringWebAppApplication {
	public static void main(String[] args) {
		ApplicationContext ctx =
		 SpringApplication.run(DemoSpringWebAppApplication.class, args);

//		for (String s : ctx.getBeanDefinitionNames()) {
//			System.out.println(s);
//		}


		DIController controller = (DIController) ctx.getBean("DIController");
		System.out.println(controller.sayHello());
		System.out.println(controller.sayHelloWorld());
		System.out.println(controller.sayHelloPrimary());
	}
}
