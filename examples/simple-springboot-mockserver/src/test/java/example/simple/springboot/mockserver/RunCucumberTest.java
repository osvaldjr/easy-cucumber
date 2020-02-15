package example.simple.springboot.mockserver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.stereotype.Component;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.github.osvaldjr.core.EasyCucumberRunner;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},
    glue = {EasyCucumberRunner.GLUE_EASY_CUCUMBER, "example.simple.springboot.mockserver.steps"},
    tags = {"not @Ignore"},
    strict = true)
public class RunCucumberTest extends EasyCucumberRunner {

  private static ClientAndServer mockServer;

  @BeforeClass
  public static void beforeClass() {
    mockServer = ClientAndServer.startClientAndServer(9005);
  }

  @AfterClass
  public static void afterClass() {
    if (mockServer != null) {
      mockServer.stop();
    }

    report();
  }
}
