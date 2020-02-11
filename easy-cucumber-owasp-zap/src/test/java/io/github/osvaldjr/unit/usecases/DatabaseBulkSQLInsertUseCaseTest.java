package io.github.osvaldjr.unit.usecases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseBulkSQLInsertUseCase;

class DatabaseBulkSQLInsertUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseBulkSQLInsertUseCase databaseBulkSQLInsertUseCase;
  @Mock private JdbcTemplate jdbcTemplate;
  @Captor private ArgumentCaptor<String> argumentCaptor;

  @Test
  void shouldExecuteSQLCorrectly() {
    String sqlFilePath = "init.sql";
    databaseBulkSQLInsertUseCase.execute(sqlFilePath);
    verify(jdbcTemplate, times(1)).execute(argumentCaptor.capture());

    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    assertThat(query, equalTo(sqlFilePath));
  }
}
