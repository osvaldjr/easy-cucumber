[![Build Status](https://travis-ci.org/osvaldjr/quick-starter-cucumber-component-test.svg?branch=master)](https://travis-ci.org/osvaldjr/quick-starter-cucumber-component-test) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=br.community%3Acomponent-test&metric=alert_status)](https://sonarcloud.io/dashboard?id=br.community%3Acomponent-test)

# Quick Starter Component Test
An easy to use, zero code Cucumber-JVM based component test

## Example
#### Gherkin existents

```gherkin
    Given I have a request with body <REQUEST FILE PATH>
    And A have a mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for <DEPENDENCY NAME>
    When I make a <HTTP METHOD> to <URI>
    Then I expect mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for <DEPENDENCY NAME> to have been called <TIMES TO YOUR MOCK SHOULD BE CALLED> times
    And I expect <RESPONSE FILE PATH> as response
```

### Gherkins to be implemented
```gherkin
    Given I make a request defined in <REQUEST SPEC FILE PATH>
    Then I expect to receive a <HTTP STATUS> status
    Then I expect to receive a <HTTP STATUS> with body <RESPONSE BODY FILE PATH>
```

## Mocks file example
In order to mock your http dependencies, you should put inside yout `data/<YOUR FEATURE FILE NAME>/mocks` the files following the format below, for each one of your dependecies.
### Request

```json
{
  "url": "/pokemon/*",
  "method": "GET",
  "body": {},
  "headers": {
    "content-type": "application/json"
  },
  "queryParams": {}
}
```

### Response
```json
{
  "headers": {},
  "status": 200,
  "body": {
    "message": "success"
  }
}
```

### Créditos
[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/0)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/0)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/1)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/1)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/2)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/2)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/3)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/3)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/4)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/4)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/5)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/5)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/6)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/6)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/7)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/7)
