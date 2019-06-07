@CleanStubby
@EnableFeatures
Feature: Integration
  This is integration feature for test all steps

  Scenario: Default steps
    Given A have a mock get for dependency integration
    When I make a GET to /test
