package Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


public class DriverFactory {
    static WebDriver driver;
    static Properties properties;
    static ChromeOptions chromeOptions;
    public static WebDriver initialize_Driver(String browser){
        properties  = ConfigReader.getProperties();

        if(browser.equals("Chrome")){
            WebDriverManager.chromedriver().setup();
            //System.setProperty("webdriver.chrome.driver","C:\\chromedriver-win64\\chromedriver.exe");
            chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
            HashMap<String, Object> chromePref = new HashMap<>();
            chromePref.put("credentials_enable_service", false);//deactivates password manager asking to save credentials
            chromePref.put("profile.password_manager_enabled", false);//deactivates password manager asking to save credentials
            chromePref.put("applicationCacheEnabled", false);
            chromePref.put("profile.default_content_setting_values.notifications", 2);
            chromeOptions.setExperimentalOption("prefs", chromePref);
            DesiredCapabilities cp = new DesiredCapabilities();
            cp.setCapability(ChromeOptions.CAPABILITY,chromeOptions);
            chromeOptions.merge(cp);
            chromeOptions.addArguments("start-maximized");
            driver = new ChromeDriver(chromeOptions);
        }
        else if (browser.equals("Firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }
        else {
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("start-maximized");
            driver = new EdgeDriver(edgeOptions);
        }

        return getDriver();
    }
    public static WebDriver getDriver(){
        return driver;
    }
}
