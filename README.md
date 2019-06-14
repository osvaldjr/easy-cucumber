# Easy Cucumber
Easy Cucumber JVM Component Test

[![Build Status](https://travis-ci.org/osvaldjr/easy-cucumber.svg?branch=master)](https://travis-ci.org/osvaldjr/easy-cucumber) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.osvaldjr%3Aeasy-cucumber) <a href="https://search.maven.org/artifact/io.github.osvaldjr/easy-cucumber"><img alt="Sonatype Nexus (Releases)" src="https://img.shields.io/nexus/r/https/oss.sonatype.org/io.github.osvaldjr/easy-cucumber.svg"></a>

## Table of Contents

[TOC]

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
        <version>0.0.1</version>
    </dependency>
</dependencies>    
<dependencyManagement>
   <dependencies>
       <dependency>
            <groupId>io.github.osvaldjr</groupId>
               <artifactId>easy-cucumber</artifactId>
           <version>0.0.1</version>
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
    glue = {
      "io.github.osvaldjr.stepdefinitions.steps"
    },
    strict = true)
public class RunCucumberTest {}

```
#### Available step definitions
`yourfile.feature`
```gherkin=
Feature: Your feature name

  Scenario: Your scenario description
    # Mocks setup
    Given A have a mock for dependency <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for <DEPENDENCY NAME>
    Then I expect mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for dependecy <DEPENDENCY NAME> to have been called <TIMES TO YOUR MOCK SHOULD BE CALLED> times
    
    # Feature toggles setup
    Given the feature <YOUR FEATURE NAME> is <FEATURE STATUS>
    Given the features toggle with status
      | name                | status |
      | <YOUR FEATURE NAME> | ENABLE |
      | <YOUR FEATURE NAME> | DISABLE|

    # Request setup
    Given I have a request with body <REQUEST FILE PATH>
    When I make a <HTTP METHOD> to <URI>
    When I make a request defined in <PATH TO REQUEST DEFINITION FILE>
    
    # Response assertions
    Then I expect to receive a <HTTP STATUS> status
    Then I expect to receive a <HTTP STATUS> with body <RESPONSE BODY FILE PATH>
    Then I expect <RESPONSE BODY FILE PATH> as response
```
| Variable            | Description         | Example                     |
| :----------------- | :------------- | :-------------------------------- |
|`PATH OF MOCK FILES FOR REQUEST AND RESPONSE`|Json files defining expected request and response to your API|LINK TO EXAMPLE|
|`DEPENDENCY NAME`|Alias for your http dependency|pokemon-service|
|`TIMES TO YOUR MOCK SHOULD BE CALLED`|Times you expect your http dependency should be called|1|
|`YOUR FEATURE NAME`|Key of your feature name defined in your application.yml|LINK TO EXAMPLE|
|`FEATURE STATUS`|Status you expect your feature when your API will be called|ENABLE\|DISABLE|
|`REQUEST FILE PATH`|Contents of the file that will be sent in the request for your API|LINK TO EXAMPLE|
|`HTTP METHOD`|Http method to be called against your API|GET\|POST\|PUT\|DELETE|
|`URI`|Your API endpoint|/api/v1/pokemon/1/pikachu|
|`PATH TO REQUEST DEFINITION FILE`|Json file defining url, method, body, headers and query params|LINK TO EXAMPLE|
|`HTTP STATUS`|Http status expected to be returned by your API|200|
|`RESPONSE BODY FILE PATH`|Contents of the response body expected to be returned by your API|LINK TO EXAMPLE|

##### Examples
- PATH OF MOCK FILES FOR REQUEST AND RESPONSE
```gherkin=
Given A have a mock for dependency pokemon-detail for pokemon-service
```
Using this step, you should put two files named `pokemon-detail-request.json` and `pokemon-detail-response.json` in your `resources/data/pokemon/mocks` folder. Easy cucumber will look to them in order to setup mock server for this request
`pokemon-detail-request.json`
```json=
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
`pokemon-detail-response.json`
```json=
{
  "headers": {},
  "status": 200,
  "body": {
    "name": "Pikachu",
    "weight":7,
    "base_experience":80
  }
}
```

- YOUR FEATURE NAME
- REQUEST FILE PATH
- PATH TO REQUEST DEFINITION FILE
- RESPONSE BODY FILE PATH


### Credits
[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/0)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/0)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/1)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/1)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/2)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/2)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/3)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/3)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/4)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/4)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/5)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/5)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/6)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/6)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/7)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/7)
