package io.github.osvaldjr.stepdefinitions.steps.transformers;

import java.util.Locale;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import io.github.osvaldjr.domains.Feature;
import io.github.osvaldjr.domains.FeatureStatus;

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
