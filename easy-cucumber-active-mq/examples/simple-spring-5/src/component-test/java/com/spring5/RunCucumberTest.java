package com.spring5;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.github.osvaldjr.EasyCucumberRunner;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/jsonReports/cucumber.json"},
    features = {"src/component-test/resources/features"}, // Here is your features folder
    glue = {"com.spring5.context", "io.github.osvaldjr.stepdefinitions.steps"},
    strict = true)
public class RunCucumberTest extends EasyCucumberRunner {}
