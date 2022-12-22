package com.agileactors.usermanagementservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Dto with required info to be returned to client after
 * {@link com.agileactors.usermanagementservice.model.User user} creation.
 *
 * @param id {@link com.agileactors.usermanagementservice.model.User User's} ID
 * @param firstName {@link com.agileactors.usermanagementservice.model.User User's} first name
 * @param lastName {@link com.agileactors.usermanagementservice.model.User User's} last name
 * @param email {@link com.agileactors.usermanagementservice.model.User User's} e-mail
 * @param createdAt {@link com.agileactors.usermanagementservice.model.User User's} creation date
 * @param updatedAt {@link com.agileactors.usermanagementservice.model.User User's} last update date
 */
public record SaveUserResponseDto(@Schema(name = "id",
                                            example = "123e4567-e89b-12d3-a456-426614174000",
                                            description = "User's unique identifier")
                                    UUID id,
                                  @Schema(name = "first_name",
                                            example = "John",
                                            description = "User's first name")
                                    String firstName,
                                  @Schema(name = "last_name",
                                            example = "Doe",
                                            description = "User's last name")
                                    String lastName,
                                  @Schema(name = "email",
                                            example = "johndoe@gmail.com",
                                            description = "User's e-mail")
                                    String email,
                                  @Schema(name = "created_at",
                                            description = "User's creation date")
                                    LocalDateTime createdAt,
                                  @Schema(name = "updated_at",
                                            description = "User's last update date")
                                    LocalDateTime updatedAt) { }