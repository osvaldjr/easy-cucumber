@CleanStubby
Feature: CleanStubby
  This feature is to test hook CleanStubby

  Scenario: Create mock error and validate
    Given A have a mock getError for dependency integration
    When I make a GET to /test
    Then I expect to receive a 500 status

  Scenario: Create mock successful and validate
    Given A have a mock getSuccessful for dependency integration
    When I make a GET to /test
    Then I expect to receive a 200 status
