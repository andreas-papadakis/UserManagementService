package com.agileactors.usermanagementservice.main;

import com.agileactors.usermanagementservice.model.User;
import com.agileactors.usermanagementservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateUserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    /**
     * Test correct update of specific user.
     * First create a user, then update all fields except ID and retrieve again.
     * Compare retrieved values with expected.
     * No check for validations as already checked in {@link CreateUserRepositoryTest}
     */
    @Test
    public void testUpdateUser() {
        User testUser = new User("testFName", "testLName", "a@a.com");
        String userIdBeforeUpdate;

        testUser = userRepository.save(testUser);
        userIdBeforeUpdate = testUser.getId();

        testUser = new User(userIdBeforeUpdate, "uptdFName", "uptdLName", "uptdMail@a.com", testUser.getCreatedAt());

        userRepository.save(testUser);
        testUser = userRepository.findById(testUser.getId()).get();

        assertEquals(userIdBeforeUpdate, testUser.getId());
        assertEquals("uptdFName", testUser.getFirstName());
        assertEquals("uptdLName", testUser.getLastName());
        assertEquals("uptdMail@a.com", testUser.getEmail());
    }
}