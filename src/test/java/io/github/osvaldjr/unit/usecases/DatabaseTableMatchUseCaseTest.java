package io.github.osvaldjr.unit.usecases;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseTableMatchUseCase;

class DatabaseTableMatchUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseTableMatchUseCase databaseTableMatchUseCase;
  @Mock private JdbcTemplate jbdTemplate;
  @Captor private ArgumentCaptor<String> argumentCaptor;
  private String tableName = "my_table";

  @Test
  void shouldReturnTrueWhenExactlyAllLinesMatch() {
    when(jbdTemplate.queryForList(anyString())).thenReturn(mockResultMap());
    boolean match = databaseTableMatchUseCase.execute(tableName, mockTableLines());
    assertTrue(match);

    verify(jbdTemplate, times(1)).queryForList(argumentCaptor.capture());

    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  @Test
  void shouldReturnTrueWhenAllLinesMatchAndDBReturnsMoreLinesThanExpected() {
    when(jbdTemplate.queryForList(anyString())).thenReturn(mockResultMap());
    boolean match = databaseTableMatchUseCase.execute(tableName, mockTableLines());
    assertTrue(match);

    verify(jbdTemplate, times(1)).queryForList(argumentCaptor.capture());
    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  @Test
  void shouldReturnFalseWhenSomeLinesMatch() {
    List<Map<String, Object>> resultList = new ArrayList<>();

    Map<String, Object> line1 = new HashMap<>();
    line1.put("name", "John Snow");
    line1.put("birth_date", "1812-01-20");
    line1.put("deaths", "1");

    Map<String, Object> line2 = new HashMap<>();
    line2.put("name", "Ned Stark");
    line2.put("birth_date", "1770-03-25");
    line2.put("deaths", "1");
    resultList.add(line2);

    when(jbdTemplate.queryForList(anyString())).thenReturn(resultList);
    Assertions.assertThrows(
        AssertionError.class, () -> databaseTableMatchUseCase.execute(tableName, mockTableLines()));

    verify(jbdTemplate, times(1)).queryForList(argumentCaptor.capture());

    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  @Test
  void shouldReturnFalseWhenNoneLinesMatch() {
    List<Map<String, Object>> resultList = new ArrayList<>();

    Map<String, Object> line1 = new HashMap<>();
    line1.put("name", "Ned Stark");
    line1.put("birth_date", "1770-03-25");
    line1.put("deaths", "1");
    resultList.add(line1);

    when(jbdTemplate.queryForList(anyString())).thenReturn(resultList);
    Assertions.assertThrows(
        AssertionError.class, () -> databaseTableMatchUseCase.execute(tableName, mockTableLines()));
    verify(jbdTemplate, times(1)).queryForList(argumentCaptor.capture());
    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  private List<String[]> mockResultList() {
    String[] line1 = {"1812-01-20", "John Snow   ", "1"};
    String[] line2 = {"1852-06-01", "Arya Stark", "0"};
    String[] line3 = {null, null, "0"};
    List<String[]> list = new ArrayList<>();
    list.add(line1);
    list.add(line2);
    list.add(line3);
    return list;
  }

  private List<Map<String, Object>> mockResultMap() {
    Map<String, Object> line1 = new HashMap<>();
    line1.put("name", "John Snow");
    line1.put("birth_date", "1812-01-20");
    line1.put("deaths", "1");

    Map<String, Object> line2 = new HashMap<>();
    line2.put("name", "Arya Stark");
    line2.put("birth_date", "1852-06-01");
    line2.put("deaths", "[0-9]");

    Map<String, Object> line3 = new TreeMap<>();
    line3.put("name", "null");
    line3.put("birth_date", "null");
    line3.put("deaths", "0");

    List<Map<String, Object>> list = new ArrayList<>();
    list.add(line1);
    list.add(line2);
    list.add(line3);
    return list;
  }

  private List<TreeMap> mockTableLines() {
    TreeMap<String, String> line1 = new TreeMap<>();
    line1.put("name", "John Snow");
    line1.put("birth_date", "1812-01-20");
    line1.put("deaths", "1");

    TreeMap<String, String> line2 = new TreeMap<>();
    line2.put("name", "Arya Stark");
    line2.put("birth_date", "1852-06-01");
    line2.put("deaths", "[0-9]");

    TreeMap<String, String> line3 = new TreeMap<>();
    line3.put("name", "null");
    line3.put("birth_date", "null");
    line3.put("deaths", "0");

    return asList(line1, line2, line3);
  }
}
