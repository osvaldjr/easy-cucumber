package io.github.osvaldjr.unit.usecases;

import static java.text.MessageFormat.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseTableSelectUseCase;

class DatabaseTableSelectUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseTableSelectUseCase databaseTableSelectUseCase;
  @Mock private EntityManager entityManager;
  @Mock private Query query;

  @Test
  void shouldExecuteSQLCorrectly() {
    String tableName = "my_table";
    when(entityManager.createNativeQuery(any())).thenReturn(query);

    databaseTableSelectUseCase.execute(tableName);

    verify(query, times(1)).getResultList();
    verify(entityManager, times(1)).createNativeQuery(format("SELECT * FROM {0}", tableName));
  }
}
