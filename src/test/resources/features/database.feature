Feature: Database
  This feature is to test database steps

  Scenario: Validate steps for database integration
    Given I want to execute sql script delete.sql
    And I want to execute sql script initdb.sql
    And the table db_test_data has the following data:
      | name            | salary | created_date |
      | Remco Evenepoel | 2000   | 2019-11-25   |
    Then the table db_test_data should have the following data:
      | name              | salary | created_date |
      | Remco Evenepoel   | 2000   | 2019-11-25   |
      | Ned Overend       | 10000  | 1998-01-01   |
      | Henrique Avancini | 65432  | 2000-09-01   |
      | Peter Sagan       | 123312 | 1990-01-26   |