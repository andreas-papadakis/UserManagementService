package com.agileactors.userManagementService;

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
													 	  "id (type CHAR(36), primary key, not null), " +
													      "first_name (type VARCHAR(100), not null), " +
													      "last_name (type VARCHAR(100), not null), " +
													 	  "email (type VARCHAR(100), not null), " +
											 			  "created_at (type DATETIME, not null, " +
													 	  "and updated_at (type DATETIME).")
								 );
	}
}