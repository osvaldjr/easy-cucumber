package io.github.osvaldjr.databases.stepdefinitions;

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
import io.github.osvaldjr.core.stepdefinitions.Steps;
import io.github.osvaldjr.core.utils.FileUtils;
import io.github.osvaldjr.databases.utils.DatabaseBulkSQLInsert;
import io.github.osvaldjr.databases.utils.DatabaseTableInsert;
import io.github.osvaldjr.databases.utils.DatabaseTableMatch;
import io.github.osvaldjr.databases.utils.DatabaseTableSelect;

public class DatabaseSteps extends Steps {

  @Autowired(required = false)
  private DatabaseTableMatch databaseTableMatch;

  @Autowired(required = false)
  private DatabaseBulkSQLInsert databaseBulkSQLInsert;

  @Autowired(required = false)
  private DatabaseTableInsert databaseTableInsert;

  @Autowired(required = false)
  private DatabaseTableSelect databaseTableSelect;

  @Autowired
  private FileUtils fileUtils;

  @Before
  public void before(Scenario scenario) {
    scenarioName = FilenameUtils.getBaseName(scenario.getUri());
  }

  @Given("I want to execute sql script ([^\"]*)")
  public void iWantToExecuteSqlScript(String scriptFile) throws IOException {
    String sql =
        fileUtils.getFileContent(
            MessageFormat.format("{0}/{1}/{2}/", scenarioName, "sql", scriptFile));
    databaseBulkSQLInsert.execute(sql);
  }

  @Given("I want to execute sql file in path ([^\"]*)")
  public void iWantToExecuteSqlFileInPath(String path) throws IOException {
    String sql = fileUtils.getFileContent(path);
    databaseBulkSQLInsert.execute(sql);
  }

  @Then("the table ([^\"]*) has the following data:")
  public void theTableHasTheFollowingData(String tableName, List<TreeMap> values) {
    databaseTableInsert.execute(tableName, values);
  }

  @Then("the table ([^\"]*) should have the following data:")
  public void theTableShouldHaveTheFollowingData(String tableName, List<Map> values) {
    boolean match = databaseTableMatch.execute(tableName, values);
    Assert.assertTrue(match);
  }

  @Then("the table ([^\"]*) is empty")
  public void theTableIsEmpty(String tableName) {
    List result = databaseTableSelect.execute(tableName);
    Assert.assertTrue(result == null || result.isEmpty());
  }
}
