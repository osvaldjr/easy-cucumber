package io.github.osvaldjr.core.stepdefinitions;

import cucumber.api.TypeRegistry;

public interface MultipleTypeConfigurer {

  void configureTypeRegistry(TypeRegistry registry);
}
