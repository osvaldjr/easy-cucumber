package io.github.osvaldjr.datasource.utils;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang.math.NumberUtils.isNumber;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseTableInsert {

  @Qualifier("easyCucumberDataSource")
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public DatabaseTableInsert(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void execute(String tableName, List<TreeMap> lines) {
    String columns = getColumns(lines.get(0));
    String values = getInsertValues(lines);
    String query = format("INSERT INTO %s (%s) VALUES %s;", tableName, columns, values);
    jdbcTemplate.execute(query);
  }

  private String getInsertValues(List<TreeMap> lines) {
    Stream<String> lineValues = lines.stream().map(line -> getSingleLineValues(line.values()));
    return lineValues.map(line -> format("(%s)", line)).collect(joining(","));
  }

  private String getSingleLineValues(Collection<Object> values) {
    return values.stream().map(this::getSingleColumnValue).collect(joining(","));
  }

  private String getSingleColumnValue(Object value) {
    return isNumber(value.toString()) ? value.toString() : format("'%s'", value);
  }

  private String getColumns(Map<String, Object> map) {
    return map.keySet().stream().map(column -> format("%s", column)).collect(joining(","));
  }
}
