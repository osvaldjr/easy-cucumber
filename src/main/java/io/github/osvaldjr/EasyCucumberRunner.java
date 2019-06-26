package io.github.osvaldjr;

import lombok.extern.slf4j.Slf4j;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.junit.AfterClass;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class EasyCucumberRunner {

  public static final String GLUE_EASY_CUCUMBER = "io.github.osvaldjr.stepdefinitions";
  private static List<String> jsonFiles = Arrays.asList("target/jsonReports/cucumber.json");

  public void configureJsonFiles(List<String> jsonFiles) {
    this.jsonFiles = jsonFiles;
  }

  @AfterClass
  public static void report() {

    if (jsonFiles.stream().noneMatch(file -> Files.exists(Paths.get(file)))) {
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
}
