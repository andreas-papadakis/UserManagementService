package com.agileactors.user_management_service.model;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.lang.NonNull;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
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

    @Column(name = "e_mail")
    @NotBlank(message = "e-mail must not be blank and in correct format.")
    @Size(min = 5, max = 100, message = "e-mail's length must not exceed 100 characters.")
    @Schema(name = "eMail", example = "johndoe@gmail.com", description = "User's e-mail")
    private String eMail;

    /**
     * Get user's unique ID
     * @return user's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Set user's unique ID
     * @param id user's ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get user's first name
     * @return user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set user's first name
     * @param firstName user's first name
     */
    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName.trim();
    }

    /**
     * Get user's last name
     * @return user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set user's last name
     * @param lastName user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    /**
     * Get user's e-mail
     * @return user's e-mail
     */
    public String geteMail() {
        return eMail;
    }

    /**
     * Set user's e-mail
     * @param eMail user's e-mail
     */
    public void seteMail(String eMail) {
        if(eMail.contains("@")) {
            if(eMail.substring(eMail.indexOf('@')).contains(".") )
                this.eMail = eMail.trim();
        }
    }

    public String toString() {
        return "ID: " + id + "\nFirst name: " + firstName + "\nLast name: " + lastName + "\ne-mail: " + eMail + "\n";
    }

    User() {
        super();
    }

    public User(String firstName, String lastName, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }
}