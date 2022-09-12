package com.agileactors.usermanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

/**
 * Dto with required fields for client to update a user in DB.
 *
 * @param userId User's ID (Not shown in client)
 *
 * @param firstName User's first name
 *
 * @param lastName User's last name
 *
 * @param email User's email
 */
@SuppressWarnings("checkstyle:LineLengthCheck")
public record UpdateUserRequestDto(@JsonIgnore UUID userId,
                                   @Schema(example = "John", description = "User's first name") String firstName, //TODO: add validations
                                   @Schema(example = "Doe", description = "User's last name") String lastName, //TODO: add validations
                                   @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail") String email) { //TODO: add validations
}