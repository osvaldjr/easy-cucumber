package io.github.osvaldjr.datasource.utils;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang.StringUtils.trim;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseTableMatch<V> {

  @Qualifier("easyCucumberDataSource")
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public DatabaseTableMatch(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public boolean execute(String tableName, List<Map<String, V>> lines) {
    String selectQuery = getSelectQuery(tableName, lines.get(0));

    List<Map<String, Object>> resultList = jdbcTemplate.queryForList(selectQuery);
    return lines.stream().allMatch(line -> matchLine(tableName, line, resultList));
  }

  private boolean matchLine(
      String tableName, Map<String, V> expectedLine, List<Map<String, Object>> allResults) {

    boolean matchAllColumns =
        allResults.stream().anyMatch(resultLine -> matchAllColumns(expectedLine, resultLine));

    if (!matchAllColumns) {
      throw new AssertionError(
          MessageFormat.format(
              "Assert failed in match columns of table {0}:\nExpected: {1}\nGot table: {2}",
              tableName, expectedLine, new ArrayList<>(allResults)));
    }

    return matchAllColumns;
  }

  private boolean matchAllColumns(Map<String, V> expectedLine, Map<String, Object> resultLine) {
    return expectedLine.entrySet().stream()
        .allMatch(expected -> matchColumn(expected.getValue(), resultLine.get(expected.getKey())));
  }

  private boolean matchColumn(V column, Object resultColumn) {
    String returnValue = trim(valueOf(resultColumn));
    String expectValue = valueOf(column);
    return returnValue.equals(expectValue) || returnValue.matches(expectValue);
  }

  private String getSelectQuery(String tableName, Map<String, V> map) {
    String columnsQuery = getColumns(map);
    return format("SELECT %s FROM %s", columnsQuery, tableName);
  }

  private String getColumns(Map<String, V> map) {
    return map.keySet().stream().map(column -> format("\"%s\"", column)).collect(joining(","));
  }
}
