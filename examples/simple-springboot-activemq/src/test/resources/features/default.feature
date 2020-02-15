@CleanQueues
Feature: Default
  This feature is to test all steps

  Scenario: Validate steps for queue integration
    Given I put message data.message in queue test_data
    When I consume message from queue test_data
    Then message contains property name
    And message does not contains property year
    And message property name has value easy-cucumber
    And message match with value from file data.message
