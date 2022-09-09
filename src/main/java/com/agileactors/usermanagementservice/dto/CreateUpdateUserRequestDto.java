package com.agileactors.usermanagementservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUpdateUserRequestDto(@Schema(example = "John", description = "User's first name") String firstName,
                                         @Schema(example = "Doe", description = "User's last name") String lastName,
                                         @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail") String email) {
}
