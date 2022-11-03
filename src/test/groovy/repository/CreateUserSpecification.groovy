package repository

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.exception.InvalidArgumentException
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.validations.ValidatorImpl

import javax.validation.Validation

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification

@SpringBootTest(classes = UserManagementServiceApplication)
class CreateUserSpecification extends Specification {
  @Autowired
  private UserRepository userRepository

  def "should create user"() {
    given: "a new user is created and the DB is empty"
    def newUser = new User(UUID.randomUUID(),
                           "testFName",
                           "testLName",
                           "a@a.com",
                           null,
                           null)
    userRepository.deleteAll()

    when: "the user is saved in DB"
    userRepository.save(newUser)

    then: "the user can be retrieved by looking for the id"
    userRepository.findById(newUser.getId()).isPresent()

    cleanup: "clear the DB afterwards"
    userRepository.deleteAll()
  }

  def "#user should fail to pass validation"() {
    expect: "user failed to pass validation"
    !Validation.buildDefaultValidatorFactory().validator.validate(user).empty

    where: "user has either blank first/last name and or e-mail and/or has more than 100 chars in first/last name and/or e-mail"
    user << [new CreateUserRequestDto("",
                                      "testLName",
                                      "a@a.com"),
             new CreateUserRequestDto("testFname",
                                      "",
                                      "a@a.com"),
             new CreateUserRequestDto(RandomStringUtils.randomAlphabetic(101),
                                      "testLName",
                                      "a@a.com"),
             new CreateUserRequestDto("lala",
                                      " ",
                                      "a@a.com"),
             new CreateUserRequestDto("lala",
                                      RandomStringUtils.randomAlphabetic(101),
                                      "a@a.com"),
             new CreateUserRequestDto("lala",
                                      "lala",
                                      " "),
             new CreateUserRequestDto("lala",
                                      "lala",
                                      RandomStringUtils.randomAlphabetic(101))]
  }

  def "should fail to pass validation due to invalid e-mail format"() {
    given: "user has invalid e-mail format"
    def invalidUser = new CreateUserRequestDto("lala",
                                               "lala",
                                               "aaaa@")
    def validator   = new ValidatorImpl()

    when: "validator checks e-mail's validity"
    validator.validateEmail(invalidUser.email)

    then: "InvalidArgumentException is thrown"
    def exception = thrown(InvalidArgumentException)

    and: "it's message is Invalid email."
    exception.message == "Invalid email."
  }
}