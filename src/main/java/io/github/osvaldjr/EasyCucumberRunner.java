package io.github.osvaldjr;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EasyCucumberRunner {

  public static final String GLUE_EASY_CUCUMBER = "io.github.osvaldjr.stepdefinitions";
  private static List<String> jsonFiles = Arrays.asList("target/jsonReports/cucumber.json");

  public synchronized void configureJsonFiles(List<String> jsonFiles) {
    this.jsonFiles = jsonFiles;
  }
}
