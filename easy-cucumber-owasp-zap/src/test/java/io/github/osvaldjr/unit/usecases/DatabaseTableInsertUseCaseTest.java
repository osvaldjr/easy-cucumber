package io.github.osvaldjr.unit.usecases;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseTableInsertUseCase;

class DatabaseTableInsertUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseTableInsertUseCase databaseTableInsertUseCase;
  @Mock private JdbcTemplate jdbcTemplate;
  @Captor private ArgumentCaptor<String> argumentCaptor;

  @Test
  void shouldExecuteSQLCorrectly() {
    String tableName = "my_table";

    databaseTableInsertUseCase.execute(tableName, mockTableLines());
    verify(jdbcTemplate, times(1)).execute(argumentCaptor.capture());

    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery =
        "INSERT INTO "
            + tableName
            + " (birth_date,deaths,name) VALUES ('1812-01-20',1,'John Snow'),('1852-06-01',0,'Arya Stark');";
    assertThat(query, equalTo(expectedQuery));
  }

  private List<TreeMap> mockTableLines() {
    TreeMap<String, String> line1 = new TreeMap<>();
    line1.put("name", "John Snow");
    line1.put("birth_date", "1812-01-20");
    line1.put("deaths", "1");

    TreeMap<String, String> line2 = new TreeMap<>();
    line2.put("name", "Arya Stark");
    line2.put("birth_date", "1852-06-01");
    line2.put("deaths", "0");

    return asList(line1, line2);
  }
}
