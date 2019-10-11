package io.github.osvaldjr.integration.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import cucumber.api.java.Before;
import io.github.osvaldjr.ApplicationConfiguration;
import io.github.osvaldjr.integration.confs.SwaggerConfig;

@SpringBootTest(
    classes = {ApplicationConfiguration.class, SwaggerConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = {"io.github.osvaldjr.integration.gateways.feign"})
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
