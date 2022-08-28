package com.agileactors.user_management_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetUserResponseDTO(@Schema(example = "John", description = "User's first name") String first_name,
                                 @Schema(example = "Doe", description = "User's last name") String last_name,
                                 @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail") String email) {
}