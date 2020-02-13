package io.github.osvaldjr.stepdefinitions.steps;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.github.osvaldjr.gateways.FileGateway;
import io.github.osvaldjr.utils.DatabaseBulkSQLInsertUseCase;
import io.github.osvaldjr.utils.DatabaseTableInsertUseCase;
import io.github.osvaldjr.utils.DatabaseTableMatchUseCase;
import io.github.osvaldjr.utils.DatabaseTableSelectUseCase;

public class DatabaseSteps extends Steps {

  private DatabaseTableMatchUseCase databaseTableMatchUseCase;
  private DatabaseBulkSQLInsertUseCase databaseBulkSQLInsertUseCase;
  private DatabaseTableInsertUseCase databaseTableInsertUseCase;
  private DatabaseTableSelectUseCase databaseTableSelectUseCase;

  private FileGateway fileGateway;
  private String scenarioName;

  @Autowired
  public DatabaseSteps(
      DatabaseTableMatchUseCase databaseTableMatchUseCase,
      DatabaseBulkSQLInsertUseCase databaseBulkSQLInsertUseCase,
      DatabaseTableInsertUseCase databaseTableInsertUseCase,
      DatabaseTableSelectUseCase databaseTableSelectUseCase,
      FileGateway fileGateway) {
    this.databaseTableMatchUseCase = databaseTableMatchUseCase;
    this.databaseBulkSQLInsertUseCase = databaseBulkSQLInsertUseCase;
    this.databaseTableInsertUseCase = databaseTableInsertUseCase;
    this.databaseTableSelectUseCase = databaseTableSelectUseCase;
    this.fileGateway = fileGateway;
  }

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Given("I want to execute sql script ([^\"]*)")
  public void iWantToExecuteSqlScript(String scriptFile) throws IOException {
    String sql =
        fileGateway.getFileContent(
            MessageFormat.format("{0}/{1}/{2}/", scenarioName, "sql", scriptFile));
    databaseBulkSQLInsertUseCase.execute(sql);
  }

  @Given("I want to execute sql file in path ([^\"]*)")
  public void iWantToExecuteSqlFileInPath(String path) throws IOException {
    String sql = fileGateway.getFileContent(path);
    databaseBulkSQLInsertUseCase.execute(sql);
  }

  @Then("the table ([^\"]*) has the following data:")
  public void theTableHasTheFollowingData(String tableName, List<TreeMap> values) {
    databaseTableInsertUseCase.execute(tableName, values);
  }

  @Then("the table ([^\"]*) should have the following data:")
  public void theTableShouldHaveTheFollowingData(String tableName, List<Map> values) {
    boolean match = databaseTableMatchUseCase.execute(tableName, values);
    Assert.assertTrue(match);
  }

  @Then("the table ([^\"]*) is empty")
  public void theTableIsEmpty(String tableName) {
    List result = databaseTableSelectUseCase.execute(tableName);
    Assert.assertTrue(result == null || result.isEmpty());
  }
}
