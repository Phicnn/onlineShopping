package other;

import Utils.ConfigReader;
import Utils.ElementHelper;
import Utils.GenericUtils;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Repetitive {
    WebDriver driver;
    Actions actions;
    GenericUtils genericUtils;
    Properties properties;
    ElementHelper elementHelper;
    public Repetitive(WebDriver driver)
    {
        this.driver =driver;
        this.actions = new Actions(driver);
        this.genericUtils = new GenericUtils(driver);
        this.properties = ConfigReader.getProperties();
        this.elementHelper = new ElementHelper(driver);
    }

    public void Waiting()
    {
        genericUtils.WaitingForLoading(By.xpath(properties.getProperty("logoXpathExpression")));
    }
    public By getTableFunctionsWithName (String iconName)
    {
        return switch (iconName) {
            case "Sing In",
                 "Logout" -> By.id("cartme");
            case "Login",
                 "Register",
                 "Wishlist",
                 "My Account",
                 "Order History",
                 "Transactions"-> By.xpath("//ul[contains(@class,'dropdown-menu')]//*[contains(text(),'"+iconName+"')]");
            case "Compare" -> By.xpath("//ul[contains(@class,'dropdown-menu')]//*[contains(text(),'compare')]");

            default -> null;
        };
    }
    public By register(String data)
    {
        return By.xpath("//input[@placeholder='"+data+"']");
    }
    public void copyTextToImageURL()
    {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to(properties.getProperty("imageToText"));
        genericUtils.timerr(200);
    }
    public String getTesseract(String getImgSrc)
    {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/test/resources");
        tesseract.setLanguage("eng");
        URL ur = null;
        try {
            ur = new URL(getImgSrc);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedImage imageurl = null;
        String data = null;
        try {
            imageurl = ImageIO.read(ur);
            File outputfile = new File("src/test/resources/test_img/ocr_area.jpg");
            ImageIO.write(imageurl, "jpg", outputfile);

            BufferedImage ImgBlackNWhite = new BufferedImage(imageurl.getWidth(),imageurl.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D graphic = ImgBlackNWhite.createGraphics();
            graphic.drawImage(imageurl, 0, 0, Color.WHITE, null);
            graphic.dispose();

            File outputfileBlackNWhite = new File("src/test/resources/test_img/ocr_area_black_and_white.jpg");
            ImageIO.write(ImgBlackNWhite, "jpg", outputfileBlackNWhite);
            data = tesseract.doOCR(ImgBlackNWhite);
        } catch (IOException | TesseractException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
        return data;
    }
    public String getTesseractWithSS(By getImgLoc)
    {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/test/resources");
        tesseract.setLanguage("eng");

        genericUtils.scrollToElement(driver.findElement(By.xpath("//*[@class=\"breadcrumb\"]//li[1]")));
        //int x = driver.findElement(getImgLoc).getLocation().x;
        //int y = driver.findElement(getImgLoc).getLocation().y;
        //int width = driver.findElement(getImgLoc).getSize().width;
        //int height = driver.findElement(getImgLoc).getSize().height;

        File ssFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String data = null;
        BufferedImage fullImg = null;
        try {
            fullImg = ImageIO.read(ssFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage destImage = fullImg.getSubimage(455, 788, 150, 35);

        try {
            ImageIO.write(destImage, "png", new File("src/test/resources/test_img/element_screenshot.png"));

            BufferedImage ImgBlackNWhite = new BufferedImage(destImage.getWidth(),destImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D graphic = ImgBlackNWhite.createGraphics();
            graphic.drawImage(destImage, 0, 0, Color.WHITE, null);
            graphic.dispose();

            File outputfileBNWhite = new File("src/test/resources/test_img/element_screenshot_black_and_white.jpg");
            ImageIO.write(ImgBlackNWhite, "jpg", outputfileBNWhite);
            data = tesseract.doOCR(ImgBlackNWhite);
        } catch (IOException | TesseractException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
        return data;
    }
    public void FileWriterBK(String email,String password)
    {

        try (PrintWriter printWriter = new PrintWriter("MailPass.text"))
        {
            printWriter.println("Email: " + email);
            printWriter.println("Password: " + password);
        } catch (FileNotFoundException  e) {
            System.err.println("Bir hata oluştu: " + e.getMessage());
        }

    }
    public String ReadFromFile()
    {
        String email, password,value;

        try (BufferedReader br = new BufferedReader(new FileReader("MailPass.text"))) {
            email = br.readLine().split(": ")[1];
            password = br.readLine().split(": ")[1];
            value = email+ "/" +password;
        } catch (IOException e) {
            System.err.println("Bir hata oluştu: " + e.getMessage());
            value = null;
        }
        return value;
    }
}
