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

  def "should get user by ID"() {
    given: "a new user is created"
    User newUser = new User(UUID.randomUUID(),
            "testFName",
            "testLName",
            "a@a.com",
            null,
            null)

    and: "user is saved in DB"
    userRepository.save(newUser)

    when: "user is retrieved from DB"
    def retrievedUser = userRepository.findById(newUser.id).get()

    then: "the id of the retrieved user is equal to the id of created user"
    retrievedUser.id == newUser.id
  }

  def "should get user by first name"() {
    given: "3 new users are created"
    User newUser1 = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null)
    User newUser2 = new User(UUID.randomUUID(),
                             "abctestFNameasdasdasdasdasd",
                             "testLastName",
                             "b@b.com",
                             null,
                             null)
    User newUser3 = new User(UUID.randomUUID(),
                             "firstName",
                             "testLastName",
                             "b@b.com",
                             null,
                             null)

    and: "users are saved in DB"
    userRepository.save(newUser1)
    userRepository.save(newUser2)
    userRepository.save(newUser3)

    when: "users with first name containing newUser1's first name are retrieved in list"
    retrievedUsers = userRepository.findByFirstNameLike("%" + newUser1.firstName + "%")

    then: "the list contains newUser1 and newUser2"
    retrievedUsers.size() == 2
    retrievedUsers.id.contains(newUser1.id)
    retrievedUsers.id.contains(newUser2.id)
  }

  def "should get user by last name"() {
    given: "3 new users are created"
    User newUser1 = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null)
    User newUser2 = new User(UUID.randomUUID(),
                             "testFName",
                             "sadfasdftestLNameasdfasdf",
                             "a@a.com",
                             null,
                             null)
    User newUser3 = new User(UUID.randomUUID(),
                             "firstName",
                             "lastName",
                             "b@b.com",
                             null,
                             null)

    and: "users are saved in DB"
    userRepository.save(newUser1)
    userRepository.save(newUser2)
    userRepository.save(newUser3)

    when: "users with last name containing newUser1's last name are retrieved in list"
    retrievedUsers = userRepository.findByLastNameLike("%" + newUser1.lastName + "%")

    then: "the list contains newUser1 and newUser2"
    retrievedUsers.size() == 2
    retrievedUsers.id.contains(newUser1.id)
    retrievedUsers.id.contains(newUser2.id)
  }

  def "should get user by first and last name"() {
    given: "4 new users are created"
    User newUser1 = new User(UUID.randomUUID(),
                             "testFName",
                             "testLName",
                             "a@a.com",
                             null,
                             null)
    User newUser2 = new User(UUID.randomUUID(),
                             "asdtestFNameasd",
                             "sadfasdftestLNameasdfasdf",
                             "a@a.com",
                             null,
                             null)
    User newUser3 = new User(UUID.randomUUID(),
                             "asdtestFName",
                             "lastName",
                             "b@b.com",
                             null,
                             null)
    User newUser4 = new User(UUID.randomUUID(),
                             "firstName",
                             "testLNameasd",
                             "b@b.com",
                             null,
                             null)

    and: "users are saved in DB"
    userRepository.save(newUser1)
    userRepository.save(newUser2)
    userRepository.save(newUser3)
    userRepository.save(newUser4)

    when: "users with last name containing newUser1's last name are retrieved in list"
    retrievedUsers = userRepository.findByFirstNameLikeAndLastNameLike(
                                      "%" + newUser1.firstName + "%",
                                      "%" + newUser1.lastName + "%")

    then: "the list contains newUser1 and newUser2"
    retrievedUsers.size() == 2
    retrievedUsers.id.contains(newUser1.id)
    retrievedUsers.id.contains(newUser2.id)
  }

  def cleanup() {
    userRepository.deleteAll()
  }
}