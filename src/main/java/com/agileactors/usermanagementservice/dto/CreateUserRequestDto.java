package com.agileactors.usermanagementservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateUserRequestDto(@Schema(example = "John", description = "User's first name")
                                   @NotBlank(message = "First name must not be blank and up to 100 characters.")
                                   @Size(min = 1, max = 100, message = "First name must not be blank and up to 100 characters.")
                                   String firstName,
                                   @Schema(example = "Doe", description = "User's last name")
                                   @NotBlank(message = "Last name must not be blank and up to 100 characters.")
                                   @Size(min = 1, max = 100, message = "Last name must not be blank and up to 100 characters.")String lastName,
                                   @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail")
                                   @NotBlank(message = "e-mail must not be blank and up to 100 characters.")
                                   @Size(min = 5, max = 100, message = "e-mail must not be blank and up to 100 characters.")
                                   String email) {
}
