package example.simple.springboot.core.steps;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import io.github.osvaldjr.core.objects.FileVariable;
import io.github.osvaldjr.core.stepdefinitions.DefaultSteps;

public class CustomSteps {

  private final DefaultSteps defaultSteps;

  @Autowired
  public CustomSteps(DefaultSteps defaultSteps) {
    this.defaultSteps = defaultSteps;
  }

  @Then("Custom Gherkin - I expect to receive a (\\d+) status with body ([^\"]*)")
  public void customGherkinIExpectToReceiveAWithBody(
      int httpStatusExpected, String responseBodyExpected) throws IOException, JSONException {
    FileVariable.register("<% name %>", "Linux");
    FileVariable.register("\"<% year %>\"", "2000");

    defaultSteps.iExpectToReceiveAWithBody(httpStatusExpected, responseBodyExpected);
  }
}
