@Default
Feature: Default
  This feature is to test all steps

  Background:
    Given I import context from open API specification "/v2/api-docs"

  Scenario: Validate passive scan
    When I remove alerts
      | url             |
      | http://.*/api.* |
      | http://.*/v2.*  |
      | http://.*/error |
    And I generate security test HTML report with name "passive-scan-report"
    And I generate the proxy session
    Then the number of risks per category should not be greater than
      | low | medium | high | informational |
      | 6   | 0      | 0    | 0             |

  Scenario: Validate active scan
    Given I remove all alerts
    And I exclude urls from scan
      | url             |
      | http://.*/api.* |
      | http://.*/v2.*  |
      | http://.*/error |
    And I import scan policy "easycucumber" from file "default.policy"
    When I run active scan
    Then I generate security test HTML report with name "active-scan-report"
    And the number of risks per category should not be greater than
      | low | medium | high | informational |
      | 1   | 0      | 0    | 0             |
