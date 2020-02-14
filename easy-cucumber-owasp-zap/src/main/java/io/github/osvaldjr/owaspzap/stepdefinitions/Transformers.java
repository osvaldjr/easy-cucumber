package io.github.osvaldjr.owaspzap.stepdefinitions;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import io.cucumber.datatable.TableEntryTransformer;
import io.github.osvaldjr.owaspzap.objects.AlertRisk;
import io.github.osvaldjr.owaspzap.objects.DataType;

public class Transformers implements TypeRegistryConfigurer {
  @Override
  public Locale locale() {
    return Locale.ENGLISH;
  }

  @Override
  public void configureTypeRegistry(TypeRegistry typeRegistry) {
    typeRegistry.defineDataTableType(
        new DataTableType(
            DataType.class,
            (TableEntryTransformer<DataType>)
                row -> new DataType(row.get("url"), row.get("name"))));

    typeRegistry.defineDataTableType(
        new DataTableType(
            AlertRisk.class,
            (TableEntryTransformer<AlertRisk>)
                row ->
                    new AlertRisk(
                        Integer.valueOf(row.get("low")),
                        Integer.valueOf(row.get("medium")),
                        Integer.valueOf(row.get("high")),
                        Integer.valueOf(row.get("informational")))));

    Transformer transformer = new Transformer();
    typeRegistry.setDefaultDataTableEntryTransformer(transformer);
    typeRegistry.setDefaultDataTableCellTransformer(transformer);
    typeRegistry.setDefaultParameterTransformer(transformer);
  }

  private class Transformer
      implements ParameterByTypeTransformer,
          TableEntryByTypeTransformer,
          TableCellByTypeTransformer {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object transform(String s, Type type) {
      return objectMapper.convertValue(s, objectMapper.constructType(type));
    }

    @Override
    public <T> T transform(
        Map<String, String> map,
        Class<T> aClass,
        TableCellByTypeTransformer tableCellByTypeTransformer) {
      return objectMapper.convertValue(map, aClass);
    }

    @Override
    public <T> T transform(String s, Class<T> aClass) {
      return objectMapper.convertValue(s, aClass);
    }
  }
}
