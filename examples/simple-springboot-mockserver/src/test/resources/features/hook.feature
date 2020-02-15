@Hook
@CleanStubby
Feature: Hook
  This feature is to test hook CleanStubby

  Scenario: Create mock error and validate
    Given I have a mock error for dependency mock_alias
    When I make a GET to /test
    Then I expect to receive a 500 status

  Scenario: Create mock successful and validate
    Given I have a mock successful for dependency mock_alias
    When I make a GET to /test
    Then I expect to receive a 200 status