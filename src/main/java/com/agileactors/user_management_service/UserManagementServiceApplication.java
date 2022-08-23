package com.agileactors.user_management_service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

	public @Bean OpenAPI userManagementAPI() {
		return new OpenAPI().info(new Info().title("User Management API")
											 .description("Simple API with CRUD operations on MySQL DB running on docker. " +
													 	  "The DB contains a single table called users with columns " +
													 	  "id (type int, primary key, not null, auto increment), " +
													      "first_name (type varchar(100), not null), " +
													      "last_name (type varchar(100), not null) " +
													 	  "and e_mail (type varchar(100), not null)")
								 );
	}
}