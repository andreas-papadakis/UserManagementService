package repository

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes = UserManagementServiceApplication)
class GetUserSpecification extends Specification {
  @Autowired
  @Subject
  private UserRepository userRepository
  private List<User> retrievedUsers

  /**
   * Clear the DB before the run of every specification
   */
  def setup() {
    userRepository.deleteAll()
  }

  def "should get all users"() {
    given: "2 new users are created"
    User newUser1 = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null)
    User newUser2 = new User(UUID.randomUUID(),
                             "testFirstName",
                             "testLastName",
                             "b@b.com",
                             null,
                             null)

    and: "users are saved in DB"
    userRepository.save(newUser1)
    userRepository.save(newUser2)

    when: "getting all users from the DB"
    retrievedUsers = userRepository.findAll()

    then: "the number of retrieved users is 2"
    retrievedUsers.size() == 2
  }
}