@DisableFeatures
Feature: DisableFeatures
  This feature is to test hook DisableFeatures

  Scenario: Enable feature toggle
    Given the feature DELETE_INTEGRATION is ENABLE
    And A have a mock deleteSuccessful for dependency integration
    And I have a request with body deleteSuccessfulBodyRequest
    When I make a DELETE to /test
    Then I expect to receive a 200 status

  Scenario: Validate if feature toggle is disable
    Given A have a mock deleteSuccessful for dependency integration
    And I have a request with body deleteSuccessfulBodyRequest
    When I make a DELETE to /test
    Then I expect to receive a 400 status
