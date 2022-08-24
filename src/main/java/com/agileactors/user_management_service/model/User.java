package com.agileactors.user_management_service.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1", description = "User's unique identifier")
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name must not be blank")
    @Size(min = 1, max = 100, message = "First name's length must not exceed 100 characters.")
    @Schema(example = "John", description = "User's first name")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name must not be blank")
    @Size(min = 1, max = 100, message = "Last name's length must not exceed 100 characters.")
    @Schema(example = "Doe", description = "User's last name")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "e-mail must not be blank and in correct format.")
    @Size(min = 5, max = 100, message = "e-mail's length must not exceed 100 characters.")
    @Schema(name = "email", example = "johndoe@gmail.com", description = "User's e-mail")
    private String email;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public String toString() {
        return "ID: " + id + "\nFirst name: " + firstName + "\nLast name: " + lastName + "\ne-mail: " + email + "\n";
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        if(email.contains("@"))
            if(email.substring(email.indexOf('@')).contains(".") )
                this.email = email.trim();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }
}