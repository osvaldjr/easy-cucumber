package io.github.osvaldjr.utils;

import io.github.osvaldjr.configs.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.text.MessageFormat.format;

@Component
@Slf4j
@ConditionalOnBean(DatabaseConfig.class)
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
