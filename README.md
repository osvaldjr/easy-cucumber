[![Build Status](https://travis-ci.org/osvaldjr/easy-cucumber.svg?branch=master)](https://travis-ci.org/osvaldjr/easy-cucumber) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber&metric=alert_status)](https://sonarcloud.io/dashboard?id=br.community%3Acomponent-test) <a href="https://search.maven.org/artifact/io.github.osvaldjr/easy-cucumber"><img alt="Sonatype Nexus (Releases)" src="https://img.shields.io/nexus/r/https/oss.sonatype.org/io.github.osvaldjr/easy-cucumber.svg"></a>

# Easy Cucumber JVM Component Test
An easy to use, zero code Cucumber-JVM based component test


## Features
* Make GET, POST, PUT and DELETE requests to your API
* Mock HTTP dependencies with request, response, request headers, response headers and desired http status using `gherkin` syntax
* Assert HTTP Status
* Assert sucessfully and failed response body
* Change FF4J features through defined step

## Setup
#### Maven dependency
```xml
<dependencies>
    <dependency>
         <groupId>io.github.osvaldjr</groupId>
            <artifactId>easy-cucumber</artifactId>
        <version>${easy.cucumber.version}</version>
    </dependency>
</dependencies>    
<dependencyManagement>
   <dependencies>
       <dependency>
            <groupId>io.github.osvaldjr</groupId>
               <artifactId>easy-cucumber</artifactId>
           <version>0.0.1-SNAPSHOT</version>
           <type>pom</type>
           <scope>import</scope>
       </dependency>
   </dependencies>
</dependencyManagement>
```

#### Junit Runner
Create an empty class that uses the Cucumber JUnit runner, configure step definitions and features path.
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},
    glue = {"classpath:br.community.component.test.stepdefinitions"},
    strict = true)
public class RunCucumberTest {}

```

## Usage
### Create a feature file
hello.feature
```gherkin
Given I make a GET to https://www.wikipedia.org/
Then I expect to receive a 200 status
```

## Run
```bash
mvn clean verify
```

## Available step definitions

```gherkin
    Given I have a request with body <REQUEST FILE PATH>
    And A have a mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for <DEPENDENCY NAME>
    When I make a <HTTP METHOD> to <URI>
    Then I expect mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for <DEPENDENCY NAME> to have been called <TIMES TO YOUR MOCK SHOULD BE CALLED> times
    And I expect <RESPONSE FILE PATH> as response
```

### Step definitions be implemented
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
