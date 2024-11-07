Feature: Login to RAP

  @login
  Scenario:As an RAP-Usrt,I want to login
    Given intializedriver
      | Browser |
      | Chrome  |
    And Set Environment
      | EnvironmrntType |
      | Staging         |
    And Login to RAP portal
      | username | password | Otp  |
      | Pmmanger | 1234     | 1234 |
