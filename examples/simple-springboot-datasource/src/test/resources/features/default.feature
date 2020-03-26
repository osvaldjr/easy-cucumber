@Default
Feature: Default
  This feature is to test all steps

  Scenario: Validate steps for database integration
    Given I want to execute sql script delete.sql
    And I want to execute sql file in path default/sql/initdb.sql
    And the table db_test_data has the following data:
      | name            | salary | created_date |
      | Remco Evenepoel | 2000   | 2019-11-25   |
    Then the table db_test_data should have the following data:
      | name              | salary   | created_date |
      | Remco Evenepoel   | [0-9]+   | 2019-11-25   |
      | Ned Overend       | 10000    | null         |
      | Henrique Avancini | 65432    | 2000-09-01   |
      | Peter Sagan       | [0-9]{6} | 1990-01-26   |

  Scenario: Validate empty table
    When I want to execute sql script delete.sql
    Then the table db_test_data is empty