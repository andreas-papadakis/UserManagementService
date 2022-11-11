package com.agileactors.usermanagementservice.service;

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto;
import com.agileactors.usermanagementservice.dto.UpdateUserRequestDto;
import com.agileactors.usermanagementservice.exception.UserNotFoundException;
import com.agileactors.usermanagementservice.model.GetUserModel;
import com.agileactors.usermanagementservice.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Interface to connect the
 * {@link com.agileactors.usermanagementservice.controller.UserController controller} with the
 * {@link com.agileactors.usermanagementservice.repository.UserRepository repository}.
 */
public interface UserService {
  /**
   * Creates new {@link com.agileactors.usermanagementservice.model.User user}.
   *
   * @param createUserRequestDto The {@link com.agileactors.usermanagementservice.model.User user}
   *                             to be uploaded
   *
   * @return The created {@link com.agileactors.usermanagementservice.model.User user} on success
   */
  User createUser(CreateUserRequestDto createUserRequestDto);

  /**
   * Retrieves all {@link com.agileactors.usermanagementservice.model.User users} from database.
   * If getUserModel contains data, filter by first and/or last name.
   *
   * @param getUserModel The {@link com.agileactors.usermanagementservice.model.GetUserModel}
   *                     containing data for filtering
   *
   * @return List with retrieved {@link com.agileactors.usermanagementservice.model.User users}
   *
   * @throws IllegalArgumentException When getUserModel's data cannot be checked
   */
  List<User> getAllUsers(GetUserModel getUserModel) throws IllegalArgumentException;

  /**
   * Retrieves a specific {@link com.agileactors.usermanagementservice.model.User user}.
   *
   * @param userId {@link com.agileactors.usermanagementservice.model.User User's} to be retrieved
   *               ID
   *
   * @return Retrieved {@link com.agileactors.usermanagementservice.model.User user}
   *
   * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not
   *     found
   */
  User getUserById(UUID userId) throws UserNotFoundException;

  /**
   * Removes {@link com.agileactors.usermanagementservice.model.User user} with id userId.
   *
   * @param userId The ID of {@link com.agileactors.usermanagementservice.model.User user} to be
   *               removed
   *
   * @throws EmptyResultDataAccessException When
   *         {@link com.agileactors.usermanagementservice.model.User user} with id userID does not
   *         exist
   */
  void deleteUser(UUID userId) throws EmptyResultDataAccessException;

  /**
   * Removes all {@link com.agileactors.usermanagementservice.model.User users}.
   */
  void deleteAllUsers();

  /**
   * Updates {@link com.agileactors.usermanagementservice.model.User user}.
   *
   * @param updatedUser The dto containing the ID of
   *                    {@link com.agileactors.usermanagementservice.model.User user} to update and
   *                    the new values
   *
   * @return The updated {@link com.agileactors.usermanagementservice.model.User user}
   *
   * @throws com.agileactors.usermanagementservice.exception.UserNotFoundException When user not
   *                                                                               found
   */
  User updateUser(UpdateUserRequestDto updatedUser) throws UserNotFoundException;
}