package repository

import com.agileactors.usermanagementservice.dto.CreateUserRequestDto
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import com.agileactors.usermanagementservice.UserManagementServiceApplication

import javax.validation.Validation

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification

@SpringBootTest(classes = UserManagementServiceApplication)
class CreateUserSpecification extends Specification {
  @Autowired
  private UserRepository userRepository

  /**
   * Tests correct creation of {@link com.agileactors.usermanagementservice.model.User user}.
   * Creates user in DB and tries to get by ID.
   */
  def "should create user"() {
    given:
    def newUser = new User(UUID.randomUUID(),
                           "testFName",
                           "testLName",
                           "a@a.com",
                           null,
                           null)
    when:
    userRepository.save(newUser)
    then:
    userRepository.findById(newUser.getId()).isPresent()
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
}