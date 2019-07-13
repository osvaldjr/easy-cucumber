package io.github.osvaldjr.unit.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseBulkSQLInsertUseCase;

class DatabaseBulkSQLInsertUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseBulkSQLInsertUseCase databaseBulkSQLInsertUseCase;
  @Mock private EntityManager entityManager;
  @Mock private Query query;

  @Test
  void shouldExecuteSQLCorrectly() {
    String sqlFilePath = "init.sql";
    when(entityManager.createNativeQuery(sqlFilePath)).thenReturn(query);
    when(query.executeUpdate()).thenReturn(1);

    databaseBulkSQLInsertUseCase.execute(sqlFilePath);

    Mockito.verify(query, times(1)).executeUpdate();
    Mockito.verify(entityManager, times(1)).createNativeQuery(sqlFilePath);
  }
}
