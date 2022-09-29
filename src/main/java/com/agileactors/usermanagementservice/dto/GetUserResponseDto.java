package com.agileactors.usermanagementservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Dto with required info to be returned to client after successful user retrieve.
 *
 * @param firstName User's first name
 *
 * @param lastName User's last name
 *
 * @param email User's email
 */
public record GetUserResponseDto(@Schema(example = "John", description = "User's first name")
                                 String firstName,
                                 @Schema(example = "Doe", description = "User's last name")
                                 String lastName,
                                 @Schema(name = "email",
                                         example = "johndoe@gmail.com",
                                         description = "User's e-mail")
                                 String email) { }