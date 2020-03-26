@Default
@CleanStubby
@CleanQueues
Feature: Default
  This feature is to test all steps

  Scenario: Validate a default steps without request
    Given I have a mock http_get_successful for dependency mock_alias
    When I make a GET to /test
    Then I expect to receive a 200 status
    And I expect mock http_get_successful for dependency mock_alias to have been called 1 times
    And I expect http_get_body_response.json as response

  Scenario: Validate steps for database integration
    Given I want to execute sql script delete.sql
    And I want to execute sql file in path default/sql/initdb.sql
    And the table db_test_data has the following data:
      | name            | salary | created_date |
      | Remco Evenepoel | 2000   | 2019-11-25   |

  Scenario: Validate steps for queue integration
    Given I put message data.message in queue test_data
    When I consume message from queue test_data
    Then message contains property name