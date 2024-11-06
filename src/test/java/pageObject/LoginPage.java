package pageObject;

import Utils.ConfigReader;
import Utils.ElementHelper;
import Utils.GenericUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import other.Repetitive;
import java.util.List;
import java.util.Properties;


public class LoginPage {
    WebDriver driver;
    ElementHelper elementHelper;
    GenericUtils genericUtils;
    Properties properties;
    Repetitive repetitive;
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        this.elementHelper = new ElementHelper(driver);
        this.genericUtils = new GenericUtils(driver);
        this.properties = ConfigReader.getProperties();
        this.repetitive = new Repetitive(driver);
    }


    By hesapPopup = By.xpath("//*[contains(@class,'user-down haccount  hlogout')]") ;
    By pageName  = By.xpath("//*[@class='breadcrumb']");
    By passwordConfirm = By.xpath("//input[@placeholder='Password Confirm']");
    By imageUrl = By.xpath("//div[@class=\"form-group required\"]//img");
    By imageElement = By.xpath("//div[contains(@class,\"form-group\")]//img");
    By captchaCode = By.id("input-captcha");
    By createdAcc = By.xpath("//div[@id='common-success']/div/div");
    By PPCheckBox = By.xpath("//input[@name=\"agree\"]");
    String[] buttonClick = {"//input[@value='","']"};
    By emailLoc = By.name("email");
    By passwordLoc = By.name("password");
    By checkAccount = By.xpath("//*[contains(text(),'My Order')]");
    By warningMassage = By.xpath("//div[@class='alert alert-danger alert-dismissible']");



    public Boolean home()
    {
        repetitive.Waiting();
        String checkURL = driver.getCurrentUrl();
        return StringUtils.containsIgnoreCase(properties.getProperty("BaseURL"),checkURL);
    }
    public void ClickButton(String ButtonName)
    {
        elementHelper.clickBK(repetitive.getTableFunctionsWithName(ButtonName));
    }
    public Boolean hesabaErisinVeSiparisleriYonetin()
    {
        return StringUtils.containsIgnoreCase(elementHelper.findElementBK(hesapPopup).getText(),"Your Account");
    }
    public Boolean CurrentPage(String nameofPage)
    {
        return StringUtils.containsIgnoreCase(elementHelper.findElementBK(pageName).getAttribute("textContent"),nameofPage);
    }

    public void EmptyField(List<List<String>> Obj)
    {
        //thread işlemi
        genericUtils.setKeyAndValueThread(Obj.get(0).get(0),genericUtils.setTestData().name().firstName());
        genericUtils.setKeyAndValueThread(Obj.get(0).get(1),genericUtils.setTestData().name().lastName());
        genericUtils.setKeyAndValueThread(Obj.get(0).get(2),genericUtils.setTestData().internet().emailAddress());
        genericUtils.setKeyAndValueThread(Obj.get(0).get(3),"+9050"+genericUtils.setTestData().number().digits(7));
        genericUtils.setKeyAndValueThread(Obj.get(0).get(4),genericUtils.setTestData().internet().password());

        elementHelper.sendKeyBK(repetitive.register(Obj.get(0).get(0)),genericUtils.getValueOfThread(Obj.get(0).get(0)));
        elementHelper.sendKeyBK(repetitive.register(Obj.get(0).get(1)),genericUtils.getValueOfThread(Obj.get(0).get(1)));
        elementHelper.sendKeyBK(repetitive.register(Obj.get(0).get(2)),genericUtils.getValueOfThread(Obj.get(0).get(2)));
        elementHelper.sendKeyBK(repetitive.register(Obj.get(0).get(3)),genericUtils.getValueOfThread(Obj.get(0).get(3)));
        elementHelper.sendKeyBK(repetitive.register(Obj.get(0).get(4)),genericUtils.getValueOfThread(Obj.get(0).get(4)));
        elementHelper.sendKeyBK(passwordConfirm,genericUtils.getValueOfThread(Obj.get(0).get(4)));

        //mail ve şifre kaydetme
        repetitive.FileWriterBK(genericUtils.getValueOfThread(Obj.get(0).get(2)),genericUtils.getValueOfThread(Obj.get(0).get(4)));
        //String[] Data = {repetitive.ReadFromFile().split("/")[0],repetitive.ReadFromFile().split("/")[1]};

        //catcha imagetotext dönüştürme işlemleri
        String getImgSrc = elementHelper.findElementBK(imageUrl).getAttribute("src");
        genericUtils.timerr(100);
        String captchaImg = repetitive.getTesseractWithSS(imageElement);
        elementHelper.sendKeyBK(captchaCode,captchaImg);
        //String captchaImg = repetitive.getTesseract(getImgSrc);
        //elementHelper.sendKeyBK(captchaCode,captchaImg);



    }
    public Boolean CreatedAccount()
    {
        return StringUtils.containsIgnoreCase(elementHelper.findElementBK(createdAcc).getText(),"HAS BEEN CREATED");
    }
    public void clickCheckBox()
    {
        elementHelper.clickBK(PPCheckBox);
    }
    public void clickButton(String btn)
    {
        elementHelper.clickBK(By.xpath(buttonClick[0]+btn+buttonClick[1]));
    }
    public void EmailAndPassword(String email,String pass)
    {
        elementHelper.sendKeyBK(emailLoc,properties.getProperty(email));
        elementHelper.sendKeyBK(passwordLoc,properties.getProperty(pass));
        genericUtils.timerr(100);
        //repetitive.Waiting();
    }
    public Boolean LoginSuccessful()
    {
        return elementHelper.findElementBK(checkAccount).isEnabled();
    }
    public Boolean NotLoginProcess()
    {
        return StringUtils.containsIgnoreCase(elementHelper.findElementBK(warningMassage).getText(),"No match for E-Mail Address and/or Password");
        //return elementHelper.findElementBK(warningMassage).isEnabled();
    }


}
