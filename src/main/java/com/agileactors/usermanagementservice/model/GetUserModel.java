package com.agileactors.usermanagementservice.model;

import org.springframework.lang.Nullable;

/**
 * Class responsible for retrieving a specific {@link User user}. In order to do so, it holds only
 * the supported filters.
 */
public record GetUserModel(@Nullable String firstName, @Nullable String lastName) {
  /**
   * Checks if {@link GetUserModel} contains any data.
   *
   * @return True if neither parameter contains data, false if at least one does
   */
  public boolean containsData() {
    return (firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank());
  }

  /**
   * Checks if {@link GetUserModel} contains only first name.
   *
   * @return True iff only first name contains data
   */
  public boolean containsOnlyFirstName() {
    return firstName != null && !firstName.isBlank() && (lastName == null || lastName.isBlank());
  }

  /**
   * Checks if {@link GetUserModel } contains only last name.
   *
   * @return True iff only last name contains data
   */
  public boolean containsOnlyLastName() {
    return (firstName == null || firstName.isBlank()) && lastName != null && !lastName.isBlank();
  }

  /**
   * Checks if {@link GetUserModel } contains both first and last name.
   *
   * @return True iff first and last name contain data
   */
  public boolean containsAllData() {
    return firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank();
  }
}