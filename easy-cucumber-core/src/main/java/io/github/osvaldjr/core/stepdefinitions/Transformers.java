package io.github.osvaldjr.core.stepdefinitions;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Reflections;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
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
    ClassLoader classLoader = this.getClass().getClassLoader();
    ResourceLoader resourceLoader = new MultiLoader(classLoader);
    ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
    Reflections reflections = new Reflections(classFinder);
    Collection<? extends MultipleTypeConfigurer> typeConfigurers =
        reflections.instantiateSubclasses(
            MultipleTypeConfigurer.class,
            Collections.singletonList(URI.create("classpath:io/github/osvaldjr/")),
            new Class[] {},
            new Object[] {});

    for (MultipleTypeConfigurer configurer : typeConfigurers) {
      configurer.configureTypeRegistry(typeRegistry);
    }

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
