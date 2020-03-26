package io.github.osvaldjr.core;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;

import lombok.extern.slf4j.Slf4j;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;

@Slf4j
public class EasyCucumberRunner {

  public static final String GLUE_EASY_CUCUMBER = "io.github.osvaldjr";
  private static List<String> jsonFiles = Arrays.asList("target/jsonReports/cucumber.json");

  @AfterClass
  public static void report() {

    if (jsonFiles.stream().noneMatch(file -> Paths.get(file).toFile().exists())) {
      log.warn("No report generated because jsons files not configured");
    } else {
      File reportOutputDirectory = new File("target");

      Configuration configuration =
          new Configuration(reportOutputDirectory, "Easy Cucumber Reports");
      configuration.setSortingMethod(SortingMethod.NATURAL);
      configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);

      ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
      reportBuilder.generateReports();
    }
  }

  public synchronized void configureJsonFiles(List<String> jsonFiles) {
    this.jsonFiles = jsonFiles;
  }
}
