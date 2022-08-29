package com.agileactors.user_management_service.main;

import com.agileactors.user_management_service.model.User;

import com.agileactors.user_management_service.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteUserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    /**
     * Test correct deletion of user. First create a user, then delete and then try to recover by id.
     */
    @Test
    public void testDeleteUser() {
        User testUser = new User("testFName", "testLName", "a@a.com");

        userRepository.save(testUser);
        userRepository.deleteById(testUser.getId());

        assertFalse(userRepository.findById(testUser.getId()).isPresent());
    }

    /**
     * Test correct deletion of all users. First create 3 users, then delete all and count the database
     */
    @Test
    public void testDeleteAllUsers() {
        User testUser1 = new User("testFName", "testLName", "a@a.com");
        User testUser2 = new User("testFName", "testLName", "a@a.com");
        User testUser3 = new User("testFName", "testLName", "a@a.com");

        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testUser3);

        userRepository.deleteAllUsers();

        assertEquals(0, userRepository.count());
    }
}