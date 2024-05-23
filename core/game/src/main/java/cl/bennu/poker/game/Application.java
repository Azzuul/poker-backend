package cl.bennu.poker.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cl.bennu.poker.game")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
