package com.agileactors.user_management_service.repository;

import com.agileactors.user_management_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieve all users from database whose first name matches the pattern of regular expression regular_expression.
     * @param regular_expression The regular expression to check if a first_name matches
     * @return List with all the users whose first name matches regular_expression
     */
    @Query(value = "SELECT * FROM users WHERE first_name REGEXP ?", nativeQuery = true)
    List<User> findByFirstName(String regular_expression);

    /**
     * Remove user with id from database and return the number of rows affected.
     * @param id The id of user to remove
     * @return The number of rows removed (0 if id not found).
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE id=?", nativeQuery = true)
    int deleteUserById(Long id);
}
