package com.agileactors.usermanagementservice.repository

import com.agileactors.usermanagementservice.UserManagementServiceApplication
import com.agileactors.usermanagementservice.model.User
import com.agileactors.usermanagementservice.repository.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes = UserManagementServiceApplication)
class UpdateUserSpecification extends Specification {
  @Autowired
  @Subject
  private UserRepository userRepository

  def "should update user"() {
    given: "DB is empty"
    userRepository.deleteAll()

    and: "a new user is created"
    User newUser = new User(UUID.randomUUID(),
                            "testFName",
                            "testLName",
                            "a@a.com",
                            null,
                            null)

    and: "the user is saved in DB"
    userRepository.save(newUser)

    when: "another user with same ID but different data is created"
    User updatedUser = new User(newUser.id,
                                "newFirstName",
                                "newLastName",
                                "neweMail",
                                 userRepository.findById(newUser.id).get().createdAt,
                                 null)

    and: "updated user is saved in DB"
    userRepository.save(updatedUser)

    and: "retrieve that user"
    def retrievedUser = userRepository.findById(newUser.id).get()

    then: "retrieved user's data are updated"
    retrievedUser.id        == newUser.id
    retrievedUser.firstName == updatedUser.firstName
    retrievedUser.lastName  == updatedUser.lastName
    retrievedUser.email     == updatedUser.email
  }
}