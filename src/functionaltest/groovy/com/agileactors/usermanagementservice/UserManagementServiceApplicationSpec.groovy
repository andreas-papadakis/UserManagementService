package com.agileactors.usermanagementservice

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserManagementServiceApplicationSpec extends Specification {

  def "should run app"() {
    expect: "app runs without any problems"
    UserManagementServiceApplication.main(new String[] {})
  }
}