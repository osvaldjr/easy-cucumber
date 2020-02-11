package io.github.osvaldjr.stepdefinitions.steps;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;
import io.github.osvaldjr.gateways.mock.MockGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {

  private final MockGateway mockGateway;

  @Autowired
  public Hooks(MockGateway mockGateway) {
    this.mockGateway = mockGateway;
  }

  @Before("@CleanStubby")
  public void cleanupStubby() {
    mockGateway.deleteAllServices();
  }
}
