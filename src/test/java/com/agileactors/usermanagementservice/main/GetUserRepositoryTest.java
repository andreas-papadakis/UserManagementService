package com.agileactors.usermanagementservice.main;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    GetUserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Test correct read of users. First create two users, then try to recover them.
     * Check for at least 2 recovered users just in case some already existed in DB.
     */
    @Test
    public void testGetAllUsers() {
        User testUser1 = new User(UUID.randomUUID(), "testFName", "testLName", "a@a.com", null, null);
        User testUser2 = new User(UUID.randomUUID(), "testFirstName", "testLastName", "b@b.com", null, null);

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        List<User> retrievedUsers = userRepository.findAll();

        assertTrue(retrievedUsers.size() >= 2);
    }

    /**
     * Test correct read of specific user. First create a user, then try to recover by id.
     */
    @Test
    public void testGetUserById() {
        User testUser1 = new User(UUID.randomUUID(), "testFName", "testLName", "a@a.com", null, null);

        userRepository.save(testUser1);

        assertNotNull(userRepository.findById(testUser1.getId()).get());
    }

    /**
     * Test correct read of all users filtered by first name. First create two users with similar first names,
     * then try to recover them.
     */
    @Test
    public void testGetAllUserFilteredByFirstName() {
        User testUser1 = new User(UUID.randomUUID(), "testFName", "testLName", "a@a.com", null, null);
        User testUser2 = new User(UUID.randomUUID(), "abctestFNameasdasdasdasdasd", "testLName", "a@a.com", null, null);

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        List<User> retrievedUsers = userRepository.findByFirstNameLike(testUser1.getFirstName());

        assertTrue(retrievedUsers.size() >= 2);
    }
}