@CleanStubby
Feature: Default
  This is default feature with default steps

  Scenario: Default steps
    Given I have a request with "request"
    And A have a mock "scenarioMock" for "serviceName"
    When I make a "GET" to "ccf1d2d2-16cd-47d9-9bb9-bcbd465b8703"
    Then I expect mock "scenarioMock" for "serviceName" to have been called 0 times
    And I expect "response" as response
