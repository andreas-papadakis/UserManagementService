package repository

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes = UserManagementServiceApplication)
class DeleteUserSpecification extends Specification {
  @Autowired
  @Subject
  private UserRepository userRepository

  /**
   * Clear the DB before the run of every specification
   */
  def setup() {
    userRepository.deleteAll()
  }

  def "should delete user"() {
    given: "a new user is created"
    User newUser = new User(UUID.randomUUID(),
                            "testFName",
                            "testLName",
                            "a@a.com",
                            null,
                            null);
    and: "saved in DB"
    userRepository.save(newUser)

    when: "the user is deleted"
    userRepository.delete(newUser)

    then: "user can no longer be retrieved"
    !userRepository.findById(newUser.id).isPresent()
  }
}