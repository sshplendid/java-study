package me.shawn.study.springh2example;

import me.shawn.study.springh2example.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringH2ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringH2ExampleApplication.class, args);
	}

	@Bean
	public CommandLineRunner test() {
		return args -> {
		    UserRepository repository = new UserRepository();
		    User user = new User();
		    user.setPassword("1234");
		    user.setId("shawn");
		    user.setName("신세훈");
		    repository.add(user);
		};
	}

}
