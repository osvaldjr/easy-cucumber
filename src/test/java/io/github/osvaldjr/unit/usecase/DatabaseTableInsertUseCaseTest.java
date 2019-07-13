package io.github.osvaldjr.unit.usecase;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecase.DatabaseTableInsertUseCase;

class DatabaseTableInsertUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseTableInsertUseCase databaseTableInsertUseCase;
  @Mock private EntityManager entityManager;
  @Mock private Query query;
  @Captor private ArgumentCaptor<String> argumentCaptor;

  @Test
  void shouldExecuteSQLCorrectly() {
    String tableName = "my_table";
    when(entityManager.createNativeQuery(any())).thenReturn(query);
    when(query.executeUpdate()).thenReturn(1);

    databaseTableInsertUseCase.execute(tableName, mockTableLines());

    verify(query, times(1)).executeUpdate();
    verify(entityManager, times(1)).createNativeQuery(argumentCaptor.capture());

    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery =
        "INSERT INTO "
            + tableName
            + " (\"birth_date\",\"deaths\",\"name\") VALUES ('1812-01-20',1,'John Snow'),('1852-06-01',0,'Arya Stark');";
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
