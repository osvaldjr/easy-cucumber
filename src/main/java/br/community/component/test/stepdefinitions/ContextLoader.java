package br.community.component.test.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.Before;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
