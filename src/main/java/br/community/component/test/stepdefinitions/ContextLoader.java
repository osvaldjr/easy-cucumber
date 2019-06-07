package br.community.component.test.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;

import br.community.component.test.ApplicationConfiguration;
import cucumber.api.java.Before;

@SpringBootTest(classes = ApplicationConfiguration.class)
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
