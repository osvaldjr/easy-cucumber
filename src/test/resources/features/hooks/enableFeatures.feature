@EnableFeatures
@Ignore
Feature: EnableFeatures
  This feature is to test hook EnableFeatures

  Scenario: Disable feature toggle
    Given the feature DELETE_INTEGRATION is DISABLE
    When I make a DELETE to /test
    Then I expect to receive a 404 status

  Scenario: Validate if feature toggle is enable
    When I make a DELETE to /test
    Then I expect to receive a 500 status
