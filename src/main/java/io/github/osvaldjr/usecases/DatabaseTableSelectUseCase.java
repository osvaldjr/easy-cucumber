package io.github.osvaldjr.usecases;

import static java.text.MessageFormat.format;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class DatabaseTableSelectUseCase {

  @PersistenceContext(unitName = "easyCucumberEntityManagerFactory")
  private EntityManager entityManager;

  public List execute(String tableName) {
    String selectQuery = format("SELECT * FROM {0}", tableName);
    return entityManager.createNativeQuery(selectQuery).getResultList();
  }
}
