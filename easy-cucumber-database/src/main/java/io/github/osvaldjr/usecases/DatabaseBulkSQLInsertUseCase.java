package io.github.osvaldjr.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBulkSQLInsertUseCase {

  @Autowired JdbcTemplate jdbcTemplate;

  public void execute(String sql) {
    jdbcTemplate.execute(sql);
  }
}
