package io.github.osvaldjr.unit.usecases;

import static java.text.MessageFormat.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseTableSelectUseCase;

class DatabaseTableSelectUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseTableSelectUseCase databaseTableSelectUseCase;
  @Mock private JdbcTemplate jdbcTemplate;

  @Test
  void shouldExecuteSQLCorrectly() {
    String tableName = "my_table";
    when(jdbcTemplate.queryForList(any(), eq(String.class))).thenReturn(Collections.emptyList());

    databaseTableSelectUseCase.execute(tableName);
    verify(jdbcTemplate, times(1))
        .queryForList(format("SELECT * FROM {0}", tableName), String.class);
  }
}
