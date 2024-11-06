package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementHelper {
    WebDriver driver;
    WebDriverWait wait;
    Actions action;
    public ElementHelper(WebDriver driver){
        this.driver =driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        this.action = new Actions(driver);
    }
    public WebElement presenceElementBK(By key){return wait.until(ExpectedConditions.presenceOfElementLocated(key));}
    public WebElement findElementBK(By key) {return this.presenceElementBK(key);}
    public void clickBK(By key) {findElementBK(key).click();}
    public void sendKeyBK(By key, String text) {findElementBK(key).sendKeys(text);}
    public void clearAndSendKeyBK(By key,String text){findElementBK(key).clear(); findElementBK(key).sendKeys(text);}
    public void actionsClick(By key){action.moveToElement(driver.findElement(key)).click().build().perform();}
    public void actionsClickAndHold(By key) {action.clickAndHold(driver.findElement(key)).build().perform();}
    public void actionsDoubleClick(By key) {action.moveToElement(driver.findElement(key)).doubleClick().build().perform();}
    public void jumpAction(By key){action.moveToElement(driver.findElement(key)).build().perform();}
    public void clickWithJS(By key) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", driver.findElement(key));
    }

}
