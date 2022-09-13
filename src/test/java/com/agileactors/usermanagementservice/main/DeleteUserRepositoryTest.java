package com.agileactors.usermanagementservice.main;

import com.agileactors.usermanagementservice.model.User;

import com.agileactors.usermanagementservice.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteUserRepositoryTest {
    private final UserRepository userRepository;

    DeleteUserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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