package Utils;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.Properties;

public class Hooks {
    WebDriver driver;
    Properties properties;
    static int loop = 0;


    @Before
    public void before(){
        properties = ConfigReader.initialize_Properties();
        String browser = properties.getProperty("browser");
        driver = DriverFactory.initialize_Driver(browser);
        driver.get(properties.getProperty("BaseURL"));
    }
    @After
    public void after(Scenario scenario) throws Throwable{
            if(loop == 0){
                Capabilities browserCap = ((RemoteWebDriver)driver).getCapabilities();
                ExtentCucumberAdapter.getCurrentStep().getExtent().setSystemInfo("Create By","Berkay KIVRAK");
                ExtentCucumberAdapter.getCurrentStep().getExtent().setSystemInfo("OS version",System.getProperty("os.version"));
                ExtentCucumberAdapter.getCurrentStep().getExtent().setSystemInfo("Java version",System.getProperty("java.version"));
                ExtentService.getInstance().setSystemInfo("Browser Name",browserCap.getBrowserName().toUpperCase());
                ExtentService.getInstance().setSystemInfo("Browser Version",browserCap.getBrowserVersion());
            }
            loop ++;
         ThreadStateHandler.removeThreadMap();
        //if (!scenario.isFailed()) {driver.quit();}
    }
    @AfterStep
    public void AddScreenshot(Scenario scenario) throws Throwable
    {
        WebDriver driver= DriverFactory.getDriver();
        if(scenario.isFailed())
        {
            File sourcePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            byte[] fileContent = FileUtils.readFileToByteArray(sourcePath);
            scenario.attach(fileContent, "image/png", "image");
        }
    }
}
