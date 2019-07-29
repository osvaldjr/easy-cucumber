package io.github.osvaldjr.usecases;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class DatabaseBulkSQLInsertUseCase {

  @PersistenceContext(unitName = "easyCucumberEntityManagerFactory")
  EntityManager entityManager;

  @Transactional
  public void execute(String sql) {
    entityManager.createNativeQuery(sql).executeUpdate();
  }
}
