package com.agileactors.usermanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto with required fields for client to update a
 * {@link com.agileactors.usermanagementservice.model.User user} in DB.
 *
 * @param userId {@link com.agileactors.usermanagementservice.model.User User's} ID (Not shown in
 *     client)
 * @param firstName {@link com.agileactors.usermanagementservice.model.User User's} first name. Must
 *                  not be blank and up to 100 chars.
 * @param lastName {@link com.agileactors.usermanagementservice.model.User User's} last name. Must
 *                  not be blank and up to 100 chars.
 * @param email {@link com.agileactors.usermanagementservice.model.User User's} e-mail. Must not be
 *              blank and from 5 up to 100 chars.
 */
public record UpdateUserRequestDto(@JsonIgnore UUID userId,
                                   @Schema(example = "John", description = "User's first name")
                                   @Size(min = 1, max = 100, message = "First name must not be "
                                                                     + "blank and up to 100 "
                                                                     + "characters.")
                                   String firstName,
                                   @Schema(example = "Doe", description = "User's last name")
                                   @Size(min = 1, max = 100, message = "Last name must not be "
                                                                     + "blank and up to 100 "
                                                                     + "characters.")
                                   String lastName,
                                   @Schema(name = "email",
                                           example = "johndoe@gmail.com",
                                           description = "User's e-mail")
                                   @Size(min = 5, max = 100, message = "e-mail must not be blank "
                                                                     + "and from 5 up to 100 "
                                                                     + "characters.")
                                   String email) { }