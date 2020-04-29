@Default
Feature: Default
  This feature is to test all steps

  Scenario: Validate a default steps without request
    When I make a GET to /test
    Then I expect to receive a 200 status
    And I expect http_get_body_response.json as response

  Scenario: Validate a default steps with request body
    Given I have a request with body http_post_body_request.json
    When I make a POST to /test
    Then Custom Gherkin - I expect to receive a 200 status with body http_post_body_response.json

  Scenario: Validate a default steps with request defined
    When I make a request defined in http_post_defined_body_request.json
    Then I expect to receive a 200 status

  Scenario: Validate a default steps with response error body
    When I make a GET to /test/error
    Then I expect to receive a 500 status with body http_get_body_error_response.json

  Scenario: Validate external target host configuration
    Given my application host is http://localhost:9000
    When I make a GET to /test
    Then response contains property name with value Linux
    And response does not contain property updatedDate

  Scenario: Validate a default steps with request defined with PATCH method
    When I make a request defined in http_patch_defined_body_request.json
    Then I expect to receive a 200 status

  Scenario: Validate a default steps with request defined with plain text body
    When I make a request defined in http_plaintext_body_defined_body_request.json
    Then I expect to receive a 200 status

  Scenario: Validate with json schema
    When I make a request defined in http_post_defined_body_request.json
    Then I expect to receive a 200 status
    And response is valid according to schema schemas/schema.json