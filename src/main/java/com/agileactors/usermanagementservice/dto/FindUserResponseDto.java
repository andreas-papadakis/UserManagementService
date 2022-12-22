package com.agileactors.usermanagementservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Dto with required info to be returned to client after successful
 * {@link com.agileactors.usermanagementservice.model.User user} retrieve.
 *
 * @param firstName {@link com.agileactors.usermanagementservice.model.User User's} first name
 * @param lastName {@link com.agileactors.usermanagementservice.model.User User's} last name
 * @param email {@link com.agileactors.usermanagementservice.model.User User's} email
 */
public record FindUserResponseDto(@Schema(example = "John", description = "User's first name")
                                 String firstName,
                                  @Schema(example = "Doe", description = "User's last name")
                                 String lastName,
                                  @Schema(name = "email",
                                         example = "johndoe@gmail.com",
                                         description = "User's e-mail")
                                 String email) { }