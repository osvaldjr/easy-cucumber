@DisableFeatures
@Ignore
Feature: DisableFeatures
  This feature is to test hook DisableFeatures

  Scenario: Enable feature toggle
    Given the feature DELETE_INTEGRATION is ENABLE
    When I make a DELETE to /test
    Then I expect to receive a 500 status

  Scenario: Validate if feature toggle is disable
    When I make a DELETE to /test
    Then I expect to receive a 404 status
