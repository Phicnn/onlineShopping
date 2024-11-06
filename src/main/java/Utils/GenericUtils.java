package Utils;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class GenericUtils {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    JavascriptExecutor js;
    SoftAssert softAssert;
    Faker testData;
    public GenericUtils(WebDriver driver)
    {
        this.driver =driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        this.actions =new Actions(driver);
        this.js = (JavascriptExecutor)driver;
        this.testData = new Faker();
        this.softAssert = new SoftAssert();
    }

    public void SwitchWindowsTabAndClose()
    {
        ArrayList<String> switchTabs= new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(switchTabs.get(1));
        driver.close();
        driver.switchTo().window(switchTabs.get(0));
    }

    public void timerr(int time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void WaitingForLoading(By element) {
        int a = 0,loop = 0,waitBefore = 0;
        do {
            try {
                timerr(50);
                loop = 2;
                driver.findElement(element);
                a+=1;
                if(a == 240) // 120 second
                {loop = 1; System.out.println(a);}
            } catch (NoSuchElementException e) {
                timerr(450);
                waitBefore += 1;
                if(waitBefore >= 10){
                    System.err.println("\n  \u001B[35m"
                            +WebDriverException.getHostInformation()+
                            "\n  FAILED! ->: "+e.getMessage().split("\\(S",2)[0]
                            +"After waiting for "+(waitBefore/2)+" seconds, the element did not appear and continued.\n");
                    break;
                    //driver.findElement(element);
                }else if (a >= 1) {break;}
            }
        }while(loop == 2);
    }

    public Boolean isFileDownloaded(String downloadPath, String fileName)
    {
        timerr(500);
        File file = new File(downloadPath);
        File[] files = file.listFiles();
        for(int i = 0; i< (files != null ? files.length : 0); i++)
            {
                if(StringUtils.containsIgnoreCase(files[i].getName(),fileName))
                {
                    return true;
                }
            }
        return false;
    }

    public void ReloadWebPage() {driver.navigate().refresh();}
    public void scrollTop(By key){js.executeScript("arguments[0].scrollTop=arguments[0].scrollTop+arguments[0].offsetHeight",driver.findElement(key));}
    public  void setKeyAndValueThread(String key, Object value) {
        ThreadStateHandler.setValue(key, value);
    }
    public String getValueOfThread(String key) {
        return ThreadStateHandler.getValue(key);
    }
    public SoftAssert getSoftAssertProcess() { return softAssert; }
    public Faker setTestData() {
        return testData;
    }
    public String setDate(String value)
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        LocalDateTime now = LocalDateTime.now();

        switch (value) {
            case "today" ->     {return dateFormat.format(now);}
            case "yesterday" -> {calendar.add(Calendar.DATE, -1);
                                Date tomorrow = calendar.getTime();
                                return dateFormat.format(tomorrow.toInstant());}
            case "tomorrow" ->  {calendar.add(Calendar.DATE, 1);
                                Date tomorrow = calendar.getTime();
                                return dateFormat.format(tomorrow.toInstant());}
            default -> {
                String[] dateZero = {value.split("/")[0],value.split("/")[1],value.split("/")[2]};
                int newDeg = Integer.parseInt(dateZero[1]);
                if(StringUtils.containsIgnoreCase(value,"/")) {
                    switch (newDeg) {
                        case 1 -> dateZero[1] = "January";
                        case 2 -> dateZero[1] = "February";
                        case 3 -> dateZero[1] = "March";
                        case 4 -> dateZero[1] = "April";
                        case 5 -> dateZero[1] = "May";
                        case 6 -> dateZero[1] = "June";
                        case 7 -> dateZero[1] = "July";
                        case 8 -> dateZero[1] = "August";
                        case 9 -> dateZero[1] = "September";
                        case 10 -> dateZero[1] = "October";
                        case 11 -> dateZero[1] = "November";
                        case 12 -> dateZero[1] = "December";
                        default -> throw new IllegalStateException("Unexpected value: " + newDeg);
                    };
                }else{System.out.println("Please change date format : day/mount/years");}
                return dateZero[0]+" "+dateZero[1]+" "+dateZero[2];
            }
        }
    }
    public List<String> elementsToStringList(List<WebElement> listOfElements) {
        List<String> stringList = new ArrayList<>();

        for (WebElement element : listOfElements) {
            stringList.add(element.getText());
        }

        return stringList;
    }
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

}
