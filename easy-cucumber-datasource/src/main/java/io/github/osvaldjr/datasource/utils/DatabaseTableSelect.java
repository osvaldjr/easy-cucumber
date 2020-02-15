package io.github.osvaldjr.datasource.utils;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseTableSelect {

  @Qualifier("easyCucumberDataSource")
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public DatabaseTableSelect(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List execute(String tableName) {
    String selectQuery = format("SELECT * FROM {0}", tableName);
    return jdbcTemplate.queryForList(selectQuery, String.class);
  }
}
