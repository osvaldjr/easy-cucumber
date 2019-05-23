package component.test.stepdefinitions;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import component.test.ApplicationConfiguration;
import component.test.feign.TargetClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SpringBootTest(classes = ApplicationConfiguration.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class DefaultSteps {

  ObjectMapper objectMapper = new ObjectMapper();
  Object request;
  Object response;

  @Autowired TargetClient feignClient;

  @Given("^I have a request with \"([^\"]*)\"$")
  public void i_have_a_request_with(String requestPayload) throws Throwable {
    InputStream inputStream =
        this.getClass().getResourceAsStream("/jsons/" + requestPayload + ".json");
    request = objectMapper.readValue(inputStream, Object.class);
  }

  @When("^I make a \"([^\"]*)\" to \"([^\"]*)\"$")
  public void i_make_a_to(String method, String uri) throws Throwable {
    response = feignClient.post(uri, request);
  }

  @Then("^I expect \"([^\"]*)\" as response$")
  public void i_expect_as_response(String responsePayload) throws Throwable {
    InputStream responseIS =
        this.getClass().getResourceAsStream("/jsons/" + responsePayload + ".json");

    String responseExpected = IOUtils.toString(responseIS, StandardCharsets.UTF_8.name());
    String responseReceived = objectMapper.writeValueAsString(response);
    JSONAssert.assertEquals(responseExpected, responseReceived, true);
    responseIS.close();
  }
}
