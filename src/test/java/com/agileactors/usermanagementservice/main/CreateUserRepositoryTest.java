package com.agileactors.usermanagementservice.main;

import com.agileactors.usermanagementservice.model.User;

import com.agileactors.usermanagementservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateUserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    /**
     * Test correct creation of user. Create user in DB and try to get by ID.
     */
    @Test
    public void testCreateUser() {
        User testUser = new User("testFName", "testLName", "a@a.com");

        userRepository.save(testUser);

        assertTrue(userRepository.findById(testUser.getId()).isPresent());
    }

    /**
     * Test that validation for not blank first name works.
     */
    @Test
    public void testValidationBlankFirstName() {
        User testUser = new User("", "testLName", "a@a.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }

    /**
     * Test that validation for no larger than 100 chars first name works.
     */
    @Test
    public void testValidationNoLargerThan1000CharsFirstName() {
        User testUser = new User(RandomStringUtils.randomAlphabetic(101), "testLName", "a@a.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }

    /**
     * Test that validation for not blank last name works.
     */
    @Test
    public void testValidationBlankLastName() {
        User testUser = new User("lala", " ", "a@a.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }

    /**
     * Test that validation for no larger than 100 chars last name works.
     */
    @Test
    public void testValidationNoLargerThan1000CharsLastName() {
        User testUser = new User("lala", RandomStringUtils.randomAlphabetic(101), "a@a.com");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }

    /**
     * Test that validation for not blank last name works.
     */
    @Test
    public void testValidationBlankeMail() {
        User testUser = new User("lala", "lala", "   ");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }

    /**
     * Test that validation for no larger than 100 chars e-mail works.
     */
    @Test
    public void testValidationNoLargerThan1000CharseMail() {
        User testUser = new User("lala", "lala", RandomStringUtils.randomAlphabetic(101));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }

    /**
     * Test that e-mail form validation works.
     */
    @Test
    public void testValidationOfeMail() {
        User testUser = new User("lala", "lala", "aaaa@");
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(testUser);

        assertFalse(violations.isEmpty());
    }
}