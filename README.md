# Easy Cucumber
[![Build Status](https://travis-ci.org/osvaldjr/easy-cucumber.svg?branch=master)](https://travis-ci.org/osvaldjr/easy-cucumber) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber-parent&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.osvaldjr%3Aeasy-cucumber-parent) <a href="https://search.maven.org/artifact/io.github.osvaldjr/easy-cucumber-parent"><img alt="Sonatype Nexus (Releases)" src="https://img.shields.io/nexus/r/https/oss.sonatype.org/io.github.osvaldjr/easy-cucumber.svg"></a> <a href="https://github.com/LVCarnevalli/javaclean"><img  src="https://img.shields.io/badge/used%20by-javaclean-green.svg"/></a>

Easy Cucumber is an easy to use, zero code, cucumber JVM based library witch offers predefined steps to test your API.

## Contents
- [Wiki](https://github.com/osvaldjr/easy-cucumber/wiki)
- [Features](https://github.com/osvaldjr/easy-cucumber#features)
- [Setup](https://github.com/osvaldjr/easy-cucumber#setup)
  - [Maven Dependency](https://github.com/osvaldjr/easy-cucumber#maven-dependency)
  - [Junit Runner](https://github.com/osvaldjr/easy-cucumber#junit-runner)
  - [Application Test](https://github.com/osvaldjr/easy-cucumber#application-test)
  - [Feature File](https://github.com/osvaldjr/easy-cucumber#feature-file)
- [Examples](https://github.com/osvaldjr/easy-cucumber/tree/master/examples)
- [Credits](https://github.com/osvaldjr/easy-cucumber#credits)

## Features
* Make GET, POST, PUT and DELETE requests to your API;
* Mock HTTP dependencies with request, response, request headers, response headers and desired http status;
* Assert HTTP Status;
* Assert successfully and failed response body;
* Execute security tests with OWASP ZAP;
* Populate database tables and assert data;
* Put, consume and assert messages in queues;
* All features use `gherkin` syntax;

## Setup
#### Maven Dependency
```xml
<dependencies>
     <dependency>
        <groupId>io.github.osvaldjr</groupId>
        <artifactId>easy-cucumber-core</artifactId>
        <version>{ALTER FOR THE LATEST VERSION}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
#### Junit Runner
Create an empty class that uses the Cucumber JUnit runner, configure step definitions and features path:
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},
    glue = {EasyCucumberRunner.GLUE_EASY_CUCUMBER},
    tags = {"not @Ignore"},
    strict = true)
public class RunCucumberTest extends EasyCucumberRunner {}
```
This example applies when the application has already started, otherwise it will be necessary to create a class to start the application.
#### Application Test
In your application test yml configuration, inform the application you endpoint will be testing:
```yaml
easycucumber.target.url: http://localhost:8080
```
For additional configuration, view Wiki.

#### Feature File
In your file.feature, add gherkin:
```gherkin
Given I make a GET to /
Then I expect to receive a 200 status
```

## Credits
[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/0)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/0)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/1)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/1)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/2)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/2)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/3)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/3)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/4)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/4)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/5)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/5)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/6)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/6)[![](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/images/7)](https://sourcerer.io/fame/osvaldjr/osvaldjr/quick-starter-cucumber-component-test/links/7)
