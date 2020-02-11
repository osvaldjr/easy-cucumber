@CleanStubby
Feature: CleanStubby
  This feature is to test hook CleanStubby

  Scenario: Create mock error and validate
    Given I have a mock error for dependency integration
    When I make a GET to /test
    Then I expect to receive a 500 status

  Scenario: Create mock successful and validate
    Given I have a mock successful for dependency integration
    When I make a GET to /test
    Then I expect to receive a 200 status
