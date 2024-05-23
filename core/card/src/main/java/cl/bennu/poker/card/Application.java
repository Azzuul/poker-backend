package cl.bennu.poker.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "cl.bennu.poker.card.repository")
@ComponentScan(basePackages = "cl.bennu.poker.card")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
