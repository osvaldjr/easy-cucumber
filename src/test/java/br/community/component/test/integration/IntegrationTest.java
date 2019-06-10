package br.community.component.test.integration;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},
    glue = {
      "br.community.component.test.integration.stepdefinitions",
      "br.community.component.test.stepdefinitions.steps"
    },
    tags = {"~@Ignore"},
    strict = true)
public class IntegrationTest {}
