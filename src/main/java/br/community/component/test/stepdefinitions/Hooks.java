package br.community.component.test.stepdefinitions;

import org.springframework.beans.factory.annotation.Autowired;

import br.community.component.test.gateways.stubby.StubbyGateway;
import cucumber.api.java.Before;

public class Hooks {

  private final StubbyGateway stubbyGateway;

  @Autowired
  public Hooks(StubbyGateway stubbyGateway) {
    this.stubbyGateway = stubbyGateway;
  }

  @Before("@CleanStubby")
  public void cleanupStubby() {
    stubbyGateway.deleteAllServices();
  }
}
