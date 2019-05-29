package component.test;

import org.springframework.beans.factory.annotation.Autowired;

import component.test.gateways.stubby.StubbyGateway;
import cucumber.api.java.Before;

public class Hooks {

  @Autowired private StubbyGateway stubbyGateway;

  @Before("@CleanStubby")
  public void cleanupStubby() {
    stubbyGateway.deleteAllServices();
  }
}
