package com.agileactors.usermanagementservice.repository.specification;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.model.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Generate custom queries.
 */
@Component
public class UserSpecification {
  /**
   * Creates and returns specification that retrieves {@link User users} whose first name contains
   * firstName.
   *
   * @param firstName The string {@link User user's} first name must contain to be retrieved
   *
   * @return The created specification
   */
  public static Specification<User> containsFirstName(String firstName) {
    return (root, query, criteriaBuilder) ->
              criteriaBuilder.like(root.get(User_.FIRST_NAME), "%" + firstName + "%");
  }

  /**
   * Creates and returns specification that retrieves {@link User users} whose last name contains
   * lastName.
   *
   * @param lastName The string {@link User user's} last name must contain to be retrieved
   *
   * @return The created specification
   */
  public static Specification<User> containsLastName(String lastName) {
    return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(root.get(User_.LAST_NAME), "%" + lastName + "%");
  }
}