package io.github.osvaldjr.integration.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import cucumber.api.java.Before;
import io.github.osvaldjr.ApplicationConfiguration;

@SpringBootTest(
    classes = ApplicationConfiguration.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = {"io.github.osvaldjr.integration.gateways.feign"})
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
