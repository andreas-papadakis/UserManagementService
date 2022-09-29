package com.agileactors.usermanagementservice.repository;

import com.agileactors.usermanagementservice.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface to interact with DB.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  /**
   * Retrieves all {@link com.agileactors.usermanagementservice.model.User users} from database
   * whose first name contains the searchTerm.
   *
   * @param searchTerm The term that {@link com.agileactors.usermanagementservice.model.User user's}
   *     first name must contain to be retrieved
   *
   * @return List with all the {@link com.agileactors.usermanagementservice.model.User users} whose
   *     first name contains searchTerm
   */
  List<User> findByFirstNameLike(String searchTerm);

  /**
   * Removes {@link com.agileactors.usermanagementservice.model.User user} with id and returns the
   * number of rows affected.
   *
   * @param id The id of {@link com.agileactors.usermanagementservice.model.User user} to remove
   *
   * @return The number of rows removed (0 if id was not found).
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM users WHERE id = ?", nativeQuery = true)
  int deleteUserById(UUID id);

  /**
   * Removes all {@link com.agileactors.usermanagementservice.model.User users} and returns the
   * number of rows affected.
   *
   * @return The number of rows removed (0 if db was empty)
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM users", nativeQuery = true)
  int deleteAllUsers();
}