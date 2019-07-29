package io.github.osvaldjr.usecases;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class DatabaseTableMatchUseCase<V> {

  @PersistenceContext(unitName = "easyCucumberEntityManagerFactory")
  EntityManager entityManager;

  public boolean execute(String tableName, List<Map<String, V>> lines) {
    String selectQuery = getSelectQuery(tableName, lines.get(0));

    List resultList = entityManager.createNativeQuery(selectQuery).getResultList();
    return lines.stream().allMatch(line -> matchLine(new ArrayList<>(line.values()), resultList));
  }

  private boolean matchLine(List<V> expectedLine, List<V[]> allResults) {
    return allResults.stream()
        .anyMatch(
            resultLine -> {
              List<V> objects = Arrays.asList((V[]) resultLine);
              return matchAllColumns(expectedLine, objects);
            });
  }

  private boolean matchAllColumns(List<V> expectedLine, List<V> resultLine) {
    return expectedLine.stream()
        .allMatch(
            column ->
                resultLine.stream()
                    .anyMatch(resultColumn -> resultColumn.toString().equals(column)));
  }

  private String getSelectQuery(String tableName, Map<String, V> map) {
    String columnsQuery = getColumns(map);
    return format("SELECT %s FROM %s", columnsQuery, tableName);
  }

  private String getColumns(Map<String, V> map) {
    return map.keySet().stream().map(column -> format("\"%s\"", column)).collect(joining(","));
  }
}
