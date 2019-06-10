@CleanStubby
@Ignore
Feature: Integration
  This feature is to test all steps

  Scenario: Validate the steps for feature toggle
    Given the feature DELETE_INTEGRATION is ENABLE
    When I make a DELETE to /test
    Then I expect to receive a 500 status
    Given the feature DELETE_INTEGRATION is DISABLE
    When I make a DELETE to /test
    Then I expect to receive a 404 status
    Given the features toggle with status
      | name               | status |
      | DELETE_INTEGRATION | true   |
    When I make a DELETE to /test
    Then I expect to receive a 500 status
    Given the features toggle with status
      | name               | status |
      | DELETE_INTEGRATION | false  |
    When I make a DELETE to /test
    Then I expect to receive a 404 status

  Scenario: Validate a default steps without request
    Given A have a mock getSuccessful for dependency integration
    When I make a GET to /test
    Then I expect to receive a 200 status
    And I expect mock getSuccessful for dependency integration to have been called 1 times
    And I expect getSuccessfulResponse as response

  Scenario: Validate a default steps with request body
    Given A have a mock postConditionalSuccessful for dependency integration
    And I have a request with body postSuccessfulBodyRequest
    When I make a POST to /test
    Then I expect to receive a 200 status with body postSuccessfulBodyResponse

  Scenario: Validate a default steps with request defined
    Given A have a mock postConditionalSuccessful for dependency integration
    When I make a request defined in postConditionalSuccessfulRequest
    Then I expect to receive a 200 status