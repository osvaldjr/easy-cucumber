# Easy Cucumber
[![Build Status](https://travis-ci.org/osvaldjr/easy-cucumber.svg?branch=master)](https://travis-ci.org/osvaldjr/easy-cucumber) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.osvaldjr%3Aeasy-cucumber) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.osvaldjr%3Aeasy-cucumber&metric=coverage)](https://sonarcloud.io/dashboard?id=io.github.osvaldjr%3Aeasy-cucumber) <a href="https://search.maven.org/artifact/io.github.osvaldjr/easy-cucumber"><img alt="Sonatype Nexus (Releases)" src="https://img.shields.io/nexus/r/https/oss.sonatype.org/io.github.osvaldjr/easy-cucumber.svg"></a> <a href="https://github.com/LVCarnevalli/javaclean"><img  src="https://img.shields.io/badge/used%20by-javaclean-green.svg"/></a>

Easy Cucumber is an easy to use, zero code, cucumber JVM based library witch offers predefined steps to test your API. Following some conventions, you can mock your application http dependencies, change your FF4j features status, execute requests and match your API responses.

<p align="center">
    <img src="https://raw.githubusercontent.com/osvaldjr/easy-cucumber/master/docs/diagram_v5.png?raw=true" width="600" align="center">
</p>

## Contents
- [Wiki](https://github.com/osvaldjr/easy-cucumber/wiki)
- [Features](https://github.com/osvaldjr/easy-cucumber#features)
- [Requirements](https://github.com/osvaldjr/easy-cucumber#requirements)
- [Setup](https://github.com/osvaldjr/easy-cucumber#setup)
  - [Maven Dependency](https://github.com/osvaldjr/easy-cucumber#maven-dependency)
  - [Junit Runner](https://github.com/osvaldjr/easy-cucumber#junit-runner)
  - [Application Test](https://github.com/osvaldjr/easy-cucumber#application-test)
  - [Feature File](https://github.com/osvaldjr/easy-cucumber#feature-file)
- [Credits](https://github.com/osvaldjr/easy-cucumber#credits)

## Features
* Make GET, POST, PUT and DELETE requests to your API;
* Mock HTTP dependencies with request, response, request headers, response headers and desired http status;
* Assert HTTP Status;
* Assert successfully and failed response body;
* Change FF4j features through defined step;
* Execute security tests with OWASP ZAP;
* Populate database tables and assert data;
* Put, consume and assert messages in queues;
* All features use `gherkin` syntax;

## Requirements
- [JDK 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/)
- [Stubby4node](https://github.com/mrak/stubby4node) _* If use mocks_
- [FF4J](https://github.com/ff4j/ff4j) _* If use features toggles_
- [OWASP ZAP](https://github.com/zaproxy/zaproxy) _* If use security test_
- [PostgreSQL](https://www.postgresql.org/), [MySQL](https://www.mysql.com/) _* If use relational databases_
- [ActiveMQ](https://activemq.apache.org/) _* If use queues_

## Setup
#### Maven Dependency
```xml
<dependencies>
    <dependency>
        <groupId>io.github.osvaldjr</groupId>
        <artifactId>easy-cucumber</artifactId>
        <version>{ALTER FOR THE LATEST VERSION}</version>
    </dependency>
</dependencies>    
<dependencyManagement>
   <dependencies>
       <dependency>
           <groupId>io.github.osvaldjr</groupId>
           <artifactId>easy-cucumber</artifactId>
           <version>{ALTER FOR THE LATEST VERSION}</version>
           <type>pom</type>
           <scope>import</scope>
       </dependency>
   </dependencies>
</dependencyManagement>
```
#### Junit Runner
Create an empty class that uses the Cucumber JUnit runner, configure step definitions and features path:
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/jsonReports/cucumber.json"},
    features = {"src/test/resources/features"}, // Here is your features folder
    glue = {
      "io.github.osvaldjr.stepdefinitions"
    },
    strict = true)
public class RunCucumberTest extends EasyCucumberRunner

```
#### Application Test
In your application test yml configuration, inform the application you endpoint will be testing:
```yaml
target.url: http://localhost:8080
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
