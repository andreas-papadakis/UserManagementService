package com.agileactors.userManagementService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UpdateUserRequestDto(@JsonIgnore UUID userId,
                                   @Schema(example = "John", description = "User's first name") String firstName,
                                   @Schema(example = "Doe", description = "User's last name") String lastName,
                                   @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail") String email) {
}
