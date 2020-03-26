package io.github.osvaldjr.core.stepdefinitions;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class Steps {

  public String scenarioName;

  public void assertJsonPathNotFound(String jsonPath, Object object) {
    assertThat(object, notNullValue());

    assertTrue(getJsonPath(jsonPath, object));
  }

  public void assertJsonPathFound(String jsonPath, Object object) {
    assertThat(object, notNullValue());

    assertFalse(getJsonPath(jsonPath, object));
  }

  private boolean getJsonPath(String jsonPath, Object object) {
    boolean pathNotFound = false;
    try {
      JsonPath.parse(object).read(jsonPath);
    } catch (PathNotFoundException e) {
      pathNotFound = true;
    }
    return pathNotFound;
  }
}
