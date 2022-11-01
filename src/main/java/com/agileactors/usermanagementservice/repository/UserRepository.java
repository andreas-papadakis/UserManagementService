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
   *                   first name must contain to be retrieved
   *
   * @return List with all the {@link com.agileactors.usermanagementservice.model.User users} whose
   *         first name contains searchTerm
   */
  List<User> findByFirstNameLike(String searchTerm);

  /**
   * Retrieves all {@link com.agileactors.usermanagementservice.model.User users} from database
   * whose last name contains the searchTerm.
   *
   * @param searchTerm The term that {@link com.agileactors.usermanagementservice.model.User user's}
   *                   last name must contain to be retrieved
   *
   * @return List with all the {@link com.agileactors.usermanagementservice.model.User users} whose
   *         last name contains searchTerm
   */
  List<User> findByLastNameLike(String searchTerm);

  /**
   * Retrieves all {@link com.agileactors.usermanagementservice.model.User users} from database
   * whose first name and last name contain firstName and lastName respectively.
   *
   * @param firstName The string that
   *                  {@link com.agileactors.usermanagementservice.model.User user's} first name
   *                  must contain to be retrieved
   * @param lastName The string that
   *                 {@link com.agileactors.usermanagementservice.model.User user's} last name
   *                 must contain to be retrieved
   *
   * @return List with all the {@link com.agileactors.usermanagementservice.model.User users} whose
   *         first and last name contain firstName and lastName respectively
   */
  List<User> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}