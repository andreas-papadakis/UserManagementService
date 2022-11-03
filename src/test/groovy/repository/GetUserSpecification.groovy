package repository

import com.agileactors.usermanagementservice.UserManagementServiceApplication
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

  /**
   * Clear the DB before the run of every specification
   */
  def setup() {
    userRepository.deleteAll()
  }


}