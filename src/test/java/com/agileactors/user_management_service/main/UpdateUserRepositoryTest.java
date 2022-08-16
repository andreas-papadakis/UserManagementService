package com.agileactors.user_management_service.main;

import com.agileactors.user_management_service.model.User;
import com.agileactors.user_management_service.repository.UserRepository;
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
        User testUser1 = new User("testFName", "testLName", "a@a.com");
        Long userIdBeforeUpdate;

        userRepository.save(testUser1);
        userIdBeforeUpdate = testUser1.getId();

        testUser1.setFirstName("uptdFName");
        testUser1.setLastName("uptdLName");
        testUser1.seteMail("uptdMail@a.com");

        userRepository.save(testUser1);
        testUser1 = userRepository.findById(testUser1.getId()).get();

        assertEquals(userIdBeforeUpdate, testUser1.getId());
        assertEquals("uptdFName", testUser1.getFirstName());
        assertEquals("uptdLName", testUser1.getLastName());
        assertEquals("uptdMail@a.com", testUser1.geteMail());
    }
}