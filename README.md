# Easy Cucumber
[![Build Status](https://travis-ci.org/osvaldjr/easy-cucumber.svg?branch=master)](https://travis-ci.org/osvaldjr/easy-cucumber) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.osvaldjr%3Aeasy-cucumber) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber&metric=coverage)](https://sonarcloud.io/dashboard?id=io.github.osvaldjr%3Aeasy-cucumber) <a href="https://search.maven.org/artifact/io.github.osvaldjr/easy-cucumber"><img alt="Sonatype Nexus (Releases)" src="https://img.shields.io/nexus/r/https/oss.sonatype.org/io.github.osvaldjr/easy-cucumber.svg"></a>

Easy Cucumber is an easy to use, zero code, cucumber JVM based library witch offers predefined steps to test your API. Following some conventions, you can mock your application http dependencies, change your FF4j features status, execute requests and match your API responses.

<p align="center">
    <img src="https://raw.githubusercontent.com/osvaldjr/easy-cucumber/master/diagram.png?raw=true" width="600" align="center">
</p>

## Table of Contents
- [Features](https://github.com/osvaldjr/easy-cucumber#features)
- [Requirements](https://github.com/osvaldjr/easy-cucumber#requirements)
- [Setup](https://github.com/osvaldjr/easy-cucumber#setup)
  - [Maven dependency](https://github.com/osvaldjr/easy-cucumber#maven-dependency)
  - [Junit runner](https://github.com/osvaldjr/easy-cucumber#junit-runner)
  - [Application yml](https://github.com/osvaldjr/easy-cucumber#application-test-yml)
  - [Full Application test yml options](https://github.com/osvaldjr/easy-cucumber#full-application-application-test-yml-options)
  - [Feature file](https://github.com/osvaldjr/easy-cucumber#feature-file)
- [Run](https://github.com/osvaldjr/easy-cucumber#run)
- [Available step definitions](https://github.com/osvaldjr/easy-cucumber#available-step-definitions)
  - [Examples](https://github.com/osvaldjr/easy-cucumber#examples)
- [Credits](https://github.com/osvaldjr/easy-cucumber#credits)

## Features
* Make GET, POST, PUT and DELETE requests to your API
* Mock HTTP dependencies with request, response, request headers, response headers and desired http status using `gherkin` syntax
* Assert HTTP Status
* Assert successfully and failed response body
* Change FF4j features through defined step

## Requirements
- [JDK 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/)
- [Stubby](https://www.npmjs.com/package/stubby) If you want to mock your application dependecies
- [FF4j](https://ff4j.github.io/) If you want to change your FF4j status

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
#### Junit runner
Create an empty class that uses the Cucumber JUnit runner, configure step definitions and features path.
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},//Here is your features folder
    glue = {
      "io.github.osvaldjr.stepdefinitions.steps"
    },
    strict = true)
public class RunCucumberTest {}

```
#### Basic Application Test yml
In your application test configuration, inform the application you endpoint will be testing
```yaml
target.url: http://localhost:8080
```

For additional configuration
#### Full Application test yml options
```yaml
target.url: http://localhost:9000 # Your application endpoint

dependencies:
  stubby.url: http://localhost:9003 # Your stubby4node endpoint
  ff4j:
    redis:
      server: localhost # FF4j redis host
      port: 6379 # FF4j redis port

logging:
  level:
    io.github.osvaldjr.gateways.feign.IntegrationClient: DEBUG

feign:
  client:
    config:
      default:
        loggerLevel: FULL

features:
  RETRY_ON_FAILURE: retry-on-failure
```

#### Feature file
```gherkin
Given I make a GET to /
Then I expect to receive a 200 status
```
## Run
If you are using FF4j and HTTP integration in your application, you need to put those services up and running before running this command
```bash
mvn clean verify
```

## Available step definitions
`yourfile.feature`
```gherkin
Feature: Your feature name

  Scenario: Your scenario description
    # Mocks setup
    Given A have a mock for dependency <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for <DEPENDENCY NAME>
    Then I expect mock <PATH OF MOCK FILES FOR REQUEST AND RESPONSE> for dependecy <DEPENDENCY NAME> to have been called <TIMES TO YOUR MOCK SHOULD BE CALLED> times
    
    # Feature toggles setup
    Given the feature <YOUR FEATURE TOGGLE NAME> is <FEATURE STATUS>
    Given the features toggle with status
      | name                | status |
      | <YOUR FEATURE TOGGLE NAME> | ENABLE |
      | <YOUR FEATURE TOGGLE NAME> | DISABLE|

    # Request setup
    Given I have a request with body <REQUEST FILE PATH>
    When I make a <HTTP METHOD> to <URI>
    When I make a request defined in <PATH TO REQUEST DEFINITION FILE>
    
    # Response assertions
    Then I expect to receive a <HTTP STATUS> status
    Then I expect to receive a <HTTP STATUS> with body <RESPONSE BODY FILE PATH>
    Then I expect <RESPONSE BODY FILE PATH> as response
```
| Parameter            | Description         | Example                     |
| :----------------- | :------------- | :-------------------------------- |
|`PATH OF MOCK FILES FOR REQUEST AND RESPONSE`|Json files defining expected request and response to your API|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber#path-of-mock-files-for-request-and-response)|
|`DEPENDENCY NAME`|Alias for your http dependency|pokemon-service|
|`TIMES TO YOUR MOCK SHOULD BE CALLED`|Times you expect your http dependency should be called|1|
|`YOUR FEATURE TOGGLE NAME`|Key of your feature name defined in your application.yml|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber#your-feature-toggle-name)|
|`FEATURE STATUS`|Status you expect your feature when your API will be called|ENABLE\|DISABLE|
|`REQUEST FILE PATH`|Contents of the file that will be sent in the request for your API|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber#request-file-path)|
|`HTTP METHOD`|Http method to be called against your API|GET\|POST\|PUT\|DELETE|
|`URI`|Your API endpoint|/api/v1/pokemon/1/pikachu|
|`PATH TO REQUEST DEFINITION FILE`|Json file defining url, method, body, headers and query params|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber#path-to-request-definition-file)|
|`HTTP STATUS`|Http status expected to be returned by your API|200|
|`RESPONSE BODY FILE PATH`|Contents of the response body expected to be returned by your API|[EXAMPLE](https://github.com/osvaldjr/easy-cucumber#response-body-file-path)|

##### Examples
- ###### `PATH OF MOCK FILES FOR REQUEST AND RESPONSE`
    Suppose you have a feature called `pokemon.feature` and your application had an GET method, wich receives a query string for search pokemons and integrates with an external dependency responsible for returning available pokemons matching your query param

    ```gherkin
    Given A have a mock for dependency pokemon-detail for pokemon-service
    ```
    Using this step, you should put two files named `pokemon-detail-request.json` and `pokemon-detail-response.json` in your `resources/data/pokemon/mocks` folder. Easy cucumber will look to them in order to setup mock server for your application dependency.
    - **`pokemon` in folder path**: the name of your feature file
    - **`pokemon-detail` in request and response file names**: the parameter you entered in your step
    - **`pokemon-service` in your gherkin**: an alias for your dependency, this parameter will be prefixed with your dependency configuration in your application.yml and the result should be placed in your application configuration
        
        If in your test application.yml you had:
        ```yaml
        dependencies.integration.url: http://localhost:9001
        ```
        Then you should put this url as your pokemon dependency in your application.yml
        ```properties
        http://localhost:9001/pokemon-service
        ```
    
    `pokemon-detail-request.json`
    ```json
    {
      "url": "/pokemon/*",
      "method": "GET",
      "body": {},
      "headers": {
        "content-type": "application/json"
      },
      "queryParams": {"name": "pikachu"}
    }
    ```
    `pokemon-detail-response.json`
    ```json
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
- ###### YOUR FEATURE TOGGLE NAME
    Lets assume your application uses a FF4j feature with `retry-on-failure` defined key, and you want enable this feature before run some steps:
    ```gherkin
      Given the feature RETRY_ON_FAILURE is ENABLE
    ```
    You have to define in your test application.yml the features you want to enable and disable using gherkin, the parameter `RETRY_ON_FAILURE` should be present followed by the key of your feature toggle configured in your FF4j
    ```yaml
    features:
      RETRY_ON_FAILURE: retry-on-failure
    ```
- ###### REQUEST FILE PATH
    If you want to define the content in request body to your application, you should use this step to tell where the body content file is located
    ```gherkin
        Given I have a request with body http_request_body
    ```
    Using this step, you should put a file `http_request_body.json` in your `resources/data/pokemon/` folder, witch will contain data you want to be sent to your application
    After that, you can use step above to make request to your application
    ```gherkin
    Given I make a POST to /
    ```
- ###### PATH TO REQUEST DEFINITION FILE
    If you want to define your entire request at one time and execute it, you can use this step
    ```gherkin
      When I make a request defined in http_request_post
    ```
    Using this step, you should put a file `http_request_post.json` in your `resources/data/pokemon/` folder, witch will contain all information necessary to make request to your application. Easy Cucumber will load file contents and execute request.
    ```json
        {
          "url": "/test",
          "method": "POST",
          "body": {
            "name": "Linux"
          },
          "headers": {},
          "queryParams": {}
        }
    ```
- ###### RESPONSE BODY FILE PATH
    With this step you can define the expected response that your API should return
    ```gherkin
    Then I expect http_response_body_expected as response
    ```
    Using this step, you should put a file `http_response_body_expected.json` in your `resources/data/pokemon/` folder. Easy cucumber will load file contents and match against the data your API returned

## Credits
[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/0)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/0)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/1)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/1)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/2)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/2)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/3)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/3)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/4)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/4)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/5)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/5)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/6)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/6)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/7)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/7)
