Feature: Security
  This feature is to test all security steps

  Background:
    Given import context from open API specification "/v2/api-docs"
    And wait passive scan running
    And verify that the proxy has captured host information

  Scenario: Validate passive scan
    Given recovery list of alerts
    And remove alerts
      | url             |
      | http://.*/api.* |
      | http://.*/v2.*  |
      | http://.*/error |
    When generate PASSIVE security test HTML report
    And generate the session proxy
    And the number of risks per category should not be greater than
      | low | medium | high | informational |
      | 4   | 0      | 0    | 0             |

  Scenario: Validate active scan
    Given remove all alerts
    And exclude urls from scan
      | url             |
      | http://.*/api.* |
      | http://.*/v2.*  |
      | http://.*/error |
    And import scan policy "default.policy"
    When run active scan
    Then recovery list of alerts
    And generate ACTIVE security test HTML report
    And the number of risks per category should not be greater than
      | low | medium | high | informational |
      | 1   | 0      | 0    | 0             |
