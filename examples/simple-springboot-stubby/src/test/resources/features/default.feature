@Default
@CleanStubby
Feature: Default
  This feature is to test all steps

  Scenario: Validate a default steps without request
    Given I have a mock http_get_successful for dependency mock_alias
    When I make a GET to /test
    Then I expect to receive a 200 status
    And I expect mock http_get_successful for dependency mock_alias to have been called 1 times
    And I expect http_get_body_response.json as response

  Scenario: Validate a default steps with request body
    Given I have a mock http_post_successful for dependency mock_alias
    And I have a request with body http_post_body_request.json
    When I make a POST to /test
    Then I expect to receive a 200 status with body http_post_body_response.json

  Scenario: Validate a default steps with request defined
    Given I have a mock http_post_successful for dependency mock_alias
    When I make a request defined in http_post_defined_body_request.json
    Then I expect to receive a 200 status

  Scenario: Validate a default steps with response error body
    When I make a GET to /test
    Then I expect to receive a 500 status with body http_get_body_error_response.json

  Scenario: Validate external target host configuration
    Given I have a mock http_post_successful_complete_payload for dependency mock_alias
    And my application host is http://localhost:9003
    Then I make a GET to /
    Then I expect to receive a 200 status with body http_get_body_complete_response.json
    Then response contains property [0].request.json.name with value Linux
    Then response does not contain property [1]updatedDate
    Then I clear all mocks
    And I make a GET to /
    And I expect to receive a 204 status

  Scenario: Validate a default steps with request defined with PATCH method
    Given I have a mock http_patch_successful for dependency mock_alias
    When I make a request defined in http_patch_defined_body_request.json
    Then I expect to receive a 200 status

  Scenario: Validate a default steps with request defined with plain text body
    Given I have a mock http_plaintext_body_successful for dependency mock_alias
    When I make a request defined in http_plaintext_body_defined_body_request.json
    Then I expect to receive a 200 status

  Scenario: Validate with json schema
    Given I have a mock http_post_successful for dependency mock_alias
    When I make a request defined in http_post_defined_body_request.json
    Then I expect to receive a 200 status
    And response is valid according to schema schemas/schema.json