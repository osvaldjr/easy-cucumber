package io.github.osvaldjr.usecase;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class DatabaseBulkSQLInsertUseCase {

  EntityManager entityManager;

  public DatabaseBulkSQLInsertUseCase(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public void execute(String sql) {
    entityManager.createNativeQuery(sql).executeUpdate();
  }
}
