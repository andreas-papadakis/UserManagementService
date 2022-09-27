package com.agileactors.usermanagementservice.main;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateUserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    UpdateUserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Test correct update of specific user.
     * First create a user, then update all fields except ID and retrieve again.
     * Compare retrieved values with expected.
     * No check for validations as already checked in {@link CreateUserRepositoryTest}
     */
    @Test
    public void testUpdateUser() {
        User testUser = new User(UUID.randomUUID(), "testFName", "testLName", "a@a.com", null, null);

        testUser = userRepository.save(testUser);

        UUID userIdBeforeUpdate = testUser.getId();

        testUser = new User(testUser.getId(), "uptdFName", "uptdLName", "uptdMail@a.com", testUser.getCreatedAt(), null);

        userRepository.save(testUser);
        testUser = userRepository.findById(testUser.getId()).get();

        assertEquals(userIdBeforeUpdate, testUser.getId());
        assertEquals("uptdFName", testUser.getFirstName());
        assertEquals("uptdLName", testUser.getLastName());
        assertEquals("uptdMail@a.com", testUser.getEmail());
    }
}