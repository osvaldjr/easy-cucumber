[![Build Status](https://travis-ci.org/osvaldjr/quick-starter-cucumber-component-test.svg?branch=master)](https://travis-ci.org/osvaldjr/quick-starter-cucumber-component-test) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=br.community%3Acomponent-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=br.community%3Acomponent-test)

# Quick Starter Component Test
An easy to use, zero code Cucumber-JVM based component test

## Example
### Gherkin

```gherkin
    Given I have a request with "<REQUEST FILE PATH>"
    And A have a mock "<PATH OF MOCK FILES FOR REQUEST AND RESPONSE>" for "<DEPENDENCY NAME>"
    When I make a "<HTTP METHOD>" to "<URI>"
    Then I expect mock "<PATH OF MOCK FILES FOR REQUEST AND RESPONSE>" for "<DEPENDENCY NAME>" to have been called <TIMES TO YOUR MOCK SHOULD BE CALLED> times
    And I expect "<RESPONSE FILE PATH>" as response
```