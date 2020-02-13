package io.github.osvaldjr.utils;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTableSelectUseCase {

  @Autowired private JdbcTemplate jdbcTemplate;

  public List execute(String tableName) {
    String selectQuery = format("SELECT * FROM {0}", tableName);
    return jdbcTemplate.queryForList(selectQuery, String.class);
  }
}
