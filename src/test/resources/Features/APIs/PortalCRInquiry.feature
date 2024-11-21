Feature: Get CR information By Cr Number for OCP

  @CRInquirytt
  Scenario:As a OCP user,I want to Inquire and Get information for OCP Commercial Register
    Given Set the Environment
      | EnvironmrntType |
      | Testing      |
    And Authenticate user
      | adUserName | adUserPassword |
      | aosama     | 1234           |
    And  validate user
      | otp  |
      | 1234 |
    Then Get CR information By CR Number
      | CR-Number  | ThirdPartyName |
      | 1010178717 | OcpPortal      |
    And validate that response show status "active"
    Then validate that inquiry logged on DB log table
    And Verify that CR number "1010178717" logged in DB
