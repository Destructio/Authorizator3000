package com.github.destructio.authorizator3000;

import com.github.destructio.authorizator3000.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Authorizator3000Application {

	public static void main(String[] args) {
		SpringApplication.run(Authorizator3000Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService) {
		return args -> {
			userService.createBasicUser();
			userService.createAdminUser();
		};
	}
}
