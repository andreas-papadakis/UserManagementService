package com.agileactors.user_management_service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Value
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "User's unique identifier")
    //TODO: replace with UUID and correct it in constructor
    Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name must not be blank")
    @Size(min = 1, max = 100, message = "First name's length must not exceed 100 characters.")
    @Schema(example = "John", description = "User's first name")
    String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name must not be blank")
    @Size(min = 1, max = 100, message = "Last name's length must not exceed 100 characters.")
    @Schema(example = "Doe", description = "User's last name")
    String lastName;

    @Column(name = "email")
    @NotBlank(message = "e-mail must not be blank and in correct format.")
    @Size(min = 5, max = 100, message = "e-mail's length must not exceed 100 characters.")
    @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail")
    String email;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDateTime updatedAt;

    /**
     * Private constructor used only for JpaRepository's reflection
     */
    private User() {
        id        = null;
        firstName = null;
        lastName  = null;
        email     = null;
        createdAt = null;
        updatedAt = null;
    }

    @JsonCreator
    public User(String firstName, String lastName, String email) {
        this.id = 300L; //not important. Right now DB sets the correct one and will be updated to UUID instead of long
        this.firstName = (firstName != null) ? firstName.trim() : null;
        this.lastName  = (lastName != null) ? lastName.trim() : null;
        this.email     = (email != null && email.contains("@") && email.substring(email.indexOf('@')).contains(".")) ? email.trim() : null;
        this.createdAt = null; //not important; fixed by annotations
        this.updatedAt = null; //not important; fixed by annotations
    }
}