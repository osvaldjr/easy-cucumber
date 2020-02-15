package io.github.osvaldjr.jms.stepdefinitions;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.osvaldjr.jms.objects.properties.QueueProperties;
import io.github.osvaldjr.jms.utils.CleanQueue;
import io.github.osvaldjr.jms.utils.GetMessageQueue;
import io.github.osvaldjr.jms.utils.PutMessageQueue;
import io.github.osvaldjr.core.stepdefinitions.Steps;
import io.github.osvaldjr.core.utils.FileUtils;

public class QueueSteps extends Steps {

  private final PutMessageQueue putMessageQueue;
  private final GetMessageQueue getMessageQueue;
  private final CleanQueue cleanQueue;
  private final FileUtils file;
  private final QueueProperties queueProperties;

  private Object message;

  @Autowired
  public QueueSteps(
      PutMessageQueue putMessageQueue,
      GetMessageQueue getMessageQueue,
      CleanQueue cleanQueue,
      FileUtils file,
      QueueProperties queueProperties) {
    this.putMessageQueue = putMessageQueue;
    this.getMessageQueue = getMessageQueue;
    this.cleanQueue = cleanQueue;
    this.file = file;
    this.queueProperties = queueProperties;
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Before("@CleanQueues")
  public void cleanupQueues() {
    queueProperties.getNames().forEach(cleanQueue::execute);
  }

  @Given("^I put message ([^\"]*) in queue ([^\"]*)$")
  public void iPutMessageInQueue(String file, String queue) throws FileNotFoundException {
    putMessageQueue.execute(scenarioName, queue, file);
  }

  @When("^I consume message from queue ([^\"]*)$")
  public void iConsumeMessageFromQueue(String queue) {
    message = getMessageQueue.execute(queue);
  }

  @Then("^message contains property ([^\"]*)$")
  public void messageContainsProperty(String jsonPath) {
    assertJsonPathFound(jsonPath, message);
  }

  @Then("^message does not contains property ([^\"]*)$")
  public void messageDoesNotContainsProperty(String jsonPath) {
    assertJsonPathNotFound(jsonPath, message);
  }

  @Then("^message property ([^\"]*) has value ([^\"]*)$")
  public void messagePropertyHasValue(String jsonPath, String value) {
    DocumentContext documentContext = JsonPath.parse(message);
    String jsonPathValue = documentContext.read(jsonPath).toString();
    assertThat(jsonPathValue, equalTo(value));
  }

  @Then("^message match with value from file ([^\"]*)$")
  public void messageMatchWithValueFromFile(String bodyFile)
      throws FileNotFoundException, JsonProcessingException, JSONException {
    String body = file.getJsonStringFromFile(scenarioName, "messages/" + bodyFile);
    JSONAssert.assertEquals(body, file.getJsonStringFromObject(message), false);
  }
}
