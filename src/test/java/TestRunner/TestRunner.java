package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
        (features = "src/test/feature",
                glue = {"stepDefinition"},
                plugin = {"pretty","html:target/cucumberReport/cucumber.html"},
                monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {

}