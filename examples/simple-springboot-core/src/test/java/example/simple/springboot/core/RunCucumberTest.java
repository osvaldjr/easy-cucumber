package example.simple.springboot.core;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.github.osvaldjr.core.EasyCucumberRunner;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    features = {"src/test/resources/features"},
    glue = {EasyCucumberRunner.GLUE_EASY_CUCUMBER, "example.simple.springboot.core.steps"},
    tags = {"not @Ignore"},
    strict = true)
public class RunCucumberTest extends EasyCucumberRunner {}
