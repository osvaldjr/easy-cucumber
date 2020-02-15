package io.github.osvaldjr.core.stepdefinitions;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;

public class Transformers implements TypeRegistryConfigurer {
  @Override
  public Locale locale() {
    return Locale.ENGLISH;
  }

  @Override
  public void configureTypeRegistry(TypeRegistry typeRegistry) {
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
