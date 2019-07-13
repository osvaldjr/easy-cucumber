@EnableFeatures
Feature: EnableFeatures
  This feature is to test hook EnableFeatures

  Scenario: Disable feature toggle
    Given the feature DELETE_INTEGRATION is DISABLE
    And A have a mock successful for dependency integration
    And I have a request with body body_request.json
    When I make a DELETE to /test
    Then I expect to receive a 400 status

  Scenario: Validate if feature toggle is enable
    Given A have a mock successful for dependency integration
    And I have a request with body body_request.json
    When I make a DELETE to /test
    Then I expect to receive a 200 status