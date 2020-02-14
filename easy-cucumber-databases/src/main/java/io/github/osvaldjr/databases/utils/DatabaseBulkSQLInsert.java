package io.github.osvaldjr.databases.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.github.osvaldjr.databases.configs.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConditionalOnBean(DatabaseConfig.class)
public class DatabaseBulkSQLInsert {

  @Qualifier("easyCucumberDataSource")
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public DatabaseBulkSQLInsert(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void execute(String sql) {
    jdbcTemplate.execute(sql);
  }
}
