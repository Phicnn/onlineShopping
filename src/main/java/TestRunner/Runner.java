package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features",
        glue     = {"stepDefinitions",
                    "Utils"},
        plugin   = {"summary",
                    "pretty",
                    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        tags     = "@LIn"
)

public class Runner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
