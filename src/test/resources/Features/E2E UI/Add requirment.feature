Feature: Add requirement
  @Add
Scenario:As a PMmanger,I want to Create new requirement

Given intializedriver
|Browser|
|Chrome|
And Set Environment
| EnvironmrntType |
| Staging         |
And Login to RAP portal
| username | password     | Otp  |
| Pmmanger     | 1234     | 1234 |
Then Go to requirement list
And Click on Add new requirement
And Assert that Add page Opened properly
#And Create requirement
