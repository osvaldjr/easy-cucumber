package br.community.component.test.stepdefinitions.steps.transformers;

import java.util.Locale;

import br.community.component.test.domains.Feature;
import br.community.component.test.domains.FeatureStatus;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

public class FeatureTransformer implements TypeRegistryConfigurer {
  @Override
  public Locale locale() {
    return Locale.ENGLISH;
  }

  @Override
  public void configureTypeRegistry(TypeRegistry typeRegistry) {
    typeRegistry.defineDataTableType(
        new DataTableType(
            Feature.class,
            (TableEntryTransformer<Feature>)
                row -> new Feature(row.get("name"), FeatureStatus.valueOf(row.get("status")))));
  }
}
