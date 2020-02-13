package io.github.osvaldjr.core.stepdefinitions;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class Steps {

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
