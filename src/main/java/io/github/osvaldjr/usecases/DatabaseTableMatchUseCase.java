package io.github.osvaldjr.usecases;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.trim;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DatabaseTableMatchUseCase<V> {

  @PersistenceContext(unitName = "easyCucumberEntityManagerFactory")
  EntityManager entityManager;

  public boolean execute(String tableName, List<Map<String, V>> lines) {
    String selectQuery = getSelectQuery(tableName, lines.get(0));

    List resultList = entityManager.createNativeQuery(selectQuery).getResultList();
    return lines.stream()
        .allMatch(line -> matchLine(tableName, new ArrayList<>(line.values()), resultList));
  }

  private boolean matchLine(String tableName, List<V> expectedLine, List<V[]> allResults) {
    boolean matchAllColumns =
        allResults.stream()
            .anyMatch(resultLine -> matchAllColumns(expectedLine, Arrays.asList((V[]) resultLine)));

    if (!matchAllColumns) {
      throw new AssertionError(
          MessageFormat.format(
              "Assert failed in match columns of table {0}:\nExpected: {1}\nGot table: {2}",
              tableName,
              expectedLine.toString(),
              allResults.stream().map(Arrays::toString).collect(toList())));
    }

    return matchAllColumns;
  }

  private boolean matchAllColumns(List<V> expectedLine, List<V> resultLine) {
    return expectedLine.stream()
        .allMatch(
            column ->
                resultLine.stream().anyMatch(resultColumn -> matchColumn(column, resultColumn)));
  }

  private boolean matchColumn(V column, V resultColumn) {
    String returnValue = trim(valueOf(resultColumn));
    String expectValue = valueOf(column);
    return returnValue.equals(expectValue) || returnValue.matches(expectValue);
  }

  private String getSelectQuery(String tableName, Map<String, V> map) {
    String columnsQuery = getColumns(map);
    return format("SELECT %s FROM %s", columnsQuery, tableName);
  }

  private String getColumns(Map<String, V> map) {
    return map.keySet().stream().map(column -> format("\"%s\"", column)).collect(joining(","));
  }
}
