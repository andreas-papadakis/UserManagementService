package com.agileactors.usermanagementservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User {
    @Id
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "User's unique identifier")
    String id;

    @Column(name = "first_name")
    @Schema(example = "John", description = "User's first name")
    String firstName;

    @Column(name = "last_name")
    @Schema(example = "Doe", description = "User's last name")
    String lastName;

    @Column(name = "email")
    @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail")
    String email;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;
}