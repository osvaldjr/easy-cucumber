package io.github.osvaldjr.owaspzap.stepdefinitions;

import java.util.Locale;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
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
  }
}
