package br.community.component.test.integration.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import br.community.component.test.ApplicationConfiguration;
import cucumber.api.java.Before;

@SpringBootTest(
    classes = ApplicationConfiguration.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = {"br.community.component.test.integration.gateways.feign"})
public class ContextLoader {

  @Before
  public void setUp() {
    // Empty method, just to setup Application Test Context
  }
}
