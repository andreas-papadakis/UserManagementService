package com.agileactors.usermanagementservice.validations

import com.agileactors.usermanagementservice.exception.InvalidArgumentException
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Specification

class ValidatorImplSpec extends Specification {
  private def validator = new ValidatorImpl()

  def "should pass validation"() {
    given: "a valid e-mail"
    def email = RandomStringUtils.randomAlphanumeric(1) + '@' +
                RandomStringUtils.randomAlphabetic(1) + '.' +
                RandomStringUtils.randomAlphabetic(1)

    expect:
    validator.validateEmail(email)
  }

  def "should fail to pass validation because email is #email"() {
    when: "validateEmail is called to validate email"
    validator.validateEmail(email)

    then: "an InvalidArgumentException is thrown"
    thrown InvalidArgumentException

    where: "email is null and not in correct format"
    email << [null, _ as String]
  }
}