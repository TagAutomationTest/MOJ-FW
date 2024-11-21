Feature: Get CR information By Cr Number

  @CRInquiry
  Scenario:As a OCP user,I want to Inquire and Get information for <active> Commercial Register
    Given Set the Environment
      | EnvironmrntType |
      | Production      |
    And Login to Takamol API
    And validate that status code is 200 Ok
    And Extract access token from Login API
    Then Get CR information By CR Number
      | CR-Number  | ThirdPartyName |
      | 1010178717 | takamol        |
    And validate that response return all CR information
    And validate that response show status "active"


  @CRInquiry
  Scenario:As a OCP user,I want to Inquire and Get information for <expired> Commercial Register
    Given Set the Environment
      | EnvironmrntType |
      | Production      |
    And Login to Takamol API
    And validate that status code is 200 Ok
    And Extract access token from Login API
    Then Get CR information By CR Number
      | CR-Number  |
      | 1010397787 |
    And validate that response return all CR information
    And validate that response show status "expired"

  @CRInquiry
  Scenario:As a OCP user,I want to Inquire and Get information for <cancelled> Commercial Register
    Given Set the Environment
      | EnvironmrntType |
      | Production      |
    And Login to Takamol API
    And validate that status code is 200 Ok
    And Extract access token from Login API
    Then Get CR information By CR Number
      | CR-Number  |
      | 1010768705 |
    And validate that response return all CR information
    And validate that response show status "cancelled"