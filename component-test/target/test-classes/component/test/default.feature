Feature: Default
  This is default feature with default steps

  Scenario: Default steps
    Given I have a request with "request"
    When  I make a "GET" to "ccf1d2d2-16cd-47d9-9bb9-bcbd465b8703"
    Then I expect "response" as response
