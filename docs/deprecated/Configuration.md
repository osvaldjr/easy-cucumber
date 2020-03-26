#### Maven dependency
```xml
<dependencies>
    <dependency>
        <groupId>io.github.osvaldjr</groupId>
        <artifactId>easy-cucumber</artifactId>
        <version>{ALTER FOR THE LATEST VERSION}</version>
    </dependency>
</dependencies>    
```
#### Junit runner
Create an empty class that uses the Cucumber JUnit runner, configure step definitions and features path.
```java
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/jsonReports/cucumber.json"},
    features = {"src/test/resources/features"}, // Here is your features folder
    glue = {
        "io.github.osvaldjr.stepdefinitions.steps"
    },
    strict = true)
public class RunCucumberTest extends EasyCucumberRunner

```
#### Basic application test yml
In your application test configuration, inform the application you endpoint will be testing
```yaml
target.url: http://localhost:8080
```

For additional configuration
#### Full application test yml options
```yaml
logging:
  level:
    io.github.osvaldjr.gateways.feign.IntegrationClient: DEBUG

feign:
  client:
    config:
      default:
        loggerLevel: FULL
        
target.url: http://localhost:9000 # Your application endpoint

dependencies:
  stubby.url: http://localhost:9003 # Your stubby4node admin endpoint
  owasp:
    overwriteDataFolder: /tmp/ # If use owasp in docker, save session files in folder
  ff4j: # Configure FF4j
    redis:
      server: localhost
      port: 6379

spring:
  datasource: # Configure relational database
    url: jdbc:postgresql://localhost:5432/YOUR_DB_NAME
    username: <USERNAME>
    password: <PASSWORD>
  activemq: # Configure ActiveMQ queue
    broker-url: tcp://localhost:61616
    user: <USERNAME>
    password: <PASSWORD>

features: # Configure feature toggles
  names:
  - <KEY_TOGGLE_1>
  - <KEY_TOGGLE_2>
  
queues: # Configure queues
  names:
  - <NAME_QUEUE_1>
  - <NAME_QUEUE_2>
```

#### Feature file
```gherkin
Given I make a GET to /
Then I expect to receive a 200 status
```
