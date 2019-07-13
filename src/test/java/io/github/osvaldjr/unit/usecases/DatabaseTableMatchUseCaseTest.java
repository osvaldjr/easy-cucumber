package io.github.osvaldjr.unit.usecases;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.osvaldjr.unit.UnitTest;
import io.github.osvaldjr.usecases.DatabaseTableMatchUseCase;

class DatabaseTableMatchUseCaseTest extends UnitTest {

  @InjectMocks private DatabaseTableMatchUseCase databaseTableMatchUseCase;
  @Mock private EntityManager entityManager;
  @Mock private Query query;
  @Captor private ArgumentCaptor<String> argumentCaptor;
  private String tableName = "my_table";

  @BeforeEach
  void beforeEach() {
    when(entityManager.createNativeQuery(any())).thenReturn(query);
  }

  @Test
  void shouldReturnTrueWhenExactlyAllLinesMatch() {
    when(query.getResultList()).thenReturn(mockResultList());
    boolean match = databaseTableMatchUseCase.execute(tableName, mockTableLines());
    assertTrue(match);

    verify(query, times(1)).getResultList();
    verify(entityManager, times(1)).createNativeQuery(argumentCaptor.capture());
    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  @Test
  void shouldReturnTrueWhenAllLinesMatchAndDBReturnsMoreLinesThanExpected() {
    List<String[]> resultList = mockResultList();
    String[] line = {"1770-03-25", "Ned Stark", "1"};
    resultList.add(line);

    when(query.getResultList()).thenReturn(resultList);
    boolean match = databaseTableMatchUseCase.execute(tableName, mockTableLines());
    assertTrue(match);
    verify(query, times(1)).getResultList();
    verify(entityManager, times(1)).createNativeQuery(argumentCaptor.capture());
    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  @Test
  void shouldReturnFalseWhenSomeLinesMatch() {
    List<String[]> resultList = new ArrayList<>();

    String[] line1 = {"1812-01-20", "John Snow", "1"};
    String[] line2 = {"1770-03-25", "Ned Stark", "1"};
    resultList.add(line1);
    resultList.add(line2);

    when(query.getResultList()).thenReturn(resultList);
    boolean match = databaseTableMatchUseCase.execute(tableName, mockTableLines());
    assertFalse(match);
    verify(query, times(1)).getResultList();
    verify(entityManager, times(1)).createNativeQuery(argumentCaptor.capture());
    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  @Test
  void shouldReturnFalseWhenNoneLinesMatch() {
    List<String[]> resultList = new ArrayList<>();
    String[] line = {"1770-03-25", "Ned Stark", "1"};
    resultList.add(line);

    when(query.getResultList()).thenReturn(resultList);
    boolean match = databaseTableMatchUseCase.execute(tableName, mockTableLines());
    assertFalse(match);
    verify(query, times(1)).getResultList();
    verify(entityManager, times(1)).createNativeQuery(argumentCaptor.capture());
    String query = argumentCaptor.getValue();
    assertThat(query, notNullValue());
    String expectedQuery = "SELECT \"birth_date\",\"deaths\",\"name\" FROM " + tableName;
    assertThat(query, equalTo(expectedQuery));
  }

  private List<String[]> mockResultList() {
    String[] line1 = {"1812-01-20", "John Snow", "1"};
    String[] line2 = {"1852-06-01", "Arya Stark", "0"};
    List<String[]> list = new ArrayList<>();
    list.add(line1);
    list.add(line2);
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
    line2.put("deaths", "0");

    return asList(line1, line2);
  }
}
