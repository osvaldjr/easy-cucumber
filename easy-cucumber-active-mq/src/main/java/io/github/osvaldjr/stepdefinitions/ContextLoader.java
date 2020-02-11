package io.github.osvaldjr.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.Before;
import io.github.osvaldjr.ApplicationConfiguration;

@SpringBootTest(classes = ApplicationConfiguration.class)
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
