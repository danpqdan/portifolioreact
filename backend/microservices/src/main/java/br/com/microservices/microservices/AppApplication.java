package br.com.microservices.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
		Dotenv dotenv = Dotenv.load();
		String usernameEmail = dotenv.get("USERNAME_EMAIL");
		System.out.println("Username Email: " + usernameEmail);

	}

}
