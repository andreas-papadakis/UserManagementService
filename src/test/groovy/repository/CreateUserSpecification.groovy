package repository

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = UserManagementServiceApplication)
class CreateUserSpecification extends Specification {
  @Autowired
  private UserRepository userRepository

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
}