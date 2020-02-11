package io.github.osvaldjr.integration;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},
    glue = {
      "io.github.osvaldjr.integration.stepdefinitions",
      "io.github.osvaldjr.stepdefinitions.steps"
    },
    tags = {"~@Ignore"},
    strict = true)
public class IntegrationTest {}
