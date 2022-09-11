package com.agileactors.usermanagementservice.repository;

import com.agileactors.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Retrieve all users from database whose first name contains the search_term.
     * @param search_term The term that first_name must contain to retrieve the user
     * @return List with all the users whose first name contains search_term
     */
    List<User> findByFirstNameLike(String search_term);

    /**
     * Remove user with id from database and return the number of rows affected.
     * @param id The id of user to remove
     * @return The number of rows removed (0 if id not found).
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE id=?", nativeQuery = true)
    int deleteUserById(String id);

    /**
     * Remove all users from database and return the number of rows affected.
     * @return The number of rows removed (0 if db was empty).
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users", nativeQuery = true)
    int deleteAllUsers();
}
