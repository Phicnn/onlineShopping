package pageObject;

import Utils.ConfigReader;
import Utils.ElementHelper;
import Utils.GenericUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import other.Repetitive;

import java.lang.reflect.Array;
import java.util.*;

public class Filters {

    WebDriver driver;
    ElementHelper elementHelper;
    GenericUtils genericUtils;
    Properties properties;
    Repetitive repetitive;
    public Filters(WebDriver driver)
    {
        this.driver = driver;
        this.elementHelper = new ElementHelper(driver);
        this.genericUtils = new GenericUtils(driver);
        this.properties = ConfigReader.getProperties();
        this.repetitive = new Repetitive(driver);
    }


    By SuccessPopup = By.xpath("//*[contains(text(),'Success!!')]");
    String[] selectTopFilters = {"(//div[@id='mySidenav']//*[contains(text(),'","')])[1]"};
    String[] selectLeftFilter = {"//div[@class='list-group catlistpage hidden-xs']//*[contains(text(),'","')]"};
    By listView = By.id("list-view");
    By gridView = By.id("grid-view");
    By shortClick = By.id("input-sort");
    By allPrdName = By.xpath("//h4[@class='protitle']");
    By allPrdPrice = By.xpath("//p[@class='price']/span[not(@class='price-old')]");
    By allPrdRating = By.xpath("//div[@class='rating']");
    By currency = By.xpath("(//form[@id='form-currency'])[1]");
    By IndianRupee = By.name("INR");
    By PoundSterling = By.name("GBP");
    By USDollar = By.name("USD");

    public void UstKatmandaBulunanSecenekler(String filterTopName)
    {
        elementHelper.clickBK(By.xpath(selectTopFilters[0]+filterTopName+selectTopFilters[1]));
        genericUtils.timerr(3000);
    }
    public void SolTaraftakiSecenekler(String filterLeftName)
    {
        elementHelper.clickBK(By.xpath(selectLeftFilter[0]+filterLeftName+selectLeftFilter[1]));
        genericUtils.timerr(3000);
    }
    public void FormatUrunleriGormekIcin(String ViewFilter)
    {
        if(ViewFilter.equals("List")){
            elementHelper.clickBK(listView);
        } else if (ViewFilter.equals("Grid")) {
            elementHelper.clickBK(gridView);
        }else {System.err.println("Please specify the correct filters");}
    }
    public void ShortBy(String shortFilters)
    {
        elementHelper.clickBK(shortClick);
        elementHelper.clickBK(By.xpath("//*[contains(text(),'"+shortFilters+"')]"));
        genericUtils.timerr(2000);
    }
    public Boolean CurrentSerial(String filters, String order)
    {
        List<String> allProduct = null;
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        String inOrder = null;
        order = order.replace(" ","").replace("\"","");
        switch (filters) {
            case "Name","name","NAME","Model","model","MODEL"  ->  {
                allProduct = genericUtils.elementsToStringList(driver.findElements(allPrdName));
                if(order.equalsIgnoreCase("A-Z")){inOrder = "sort";
            }else {inOrder = "reverseOrder";}
            }
            case "Price","price","PRICE" -> {
                allProduct = genericUtils.elementsToStringList(driver.findElements(allPrdPrice));
                if(order.equalsIgnoreCase("Low>High")){inOrder = "sort";
                }else {inOrder = "reverseOrder";}
            }
            case "Rating","rating","RATING" -> {
                allProduct = genericUtils.elementsToStringList(driver.findElements(allPrdRating));
                if(order.equalsIgnoreCase("Low>High")){inOrder = "sort";
                }else {inOrder = "reverseOrder";}
            }
            default -> throw new IllegalStateException("Unexpected value: " + filters);
        }

        if(inOrder.equals("sort")){
            stringList = allProduct;
            if(filters.equalsIgnoreCase("Price")) {
                for (int a = 0; a < stringList.size(); a++) {
                    if(StringUtils.containsIgnoreCase(stringList.get(a),"₹")){
                        stringList.set(a, stringList.get(a).replace("₹", ""));
                    } else if (StringUtils.containsIgnoreCase(stringList.get(a),"£")) {
                        stringList.set(a, stringList.get(a).replace("£", ""));
                    }else {stringList.set(a, stringList.get(a).replace("$", ""));}
                }
                for (String str : stringList) {
                    int number = Integer.parseInt(str.split("\\.")[0]);
                    integerList.add(number);
                }
                Collections.sort(integerList);
                for (int b = 0; b < stringList.size(); b++) {
                    stringList.set(b,String.valueOf(integerList.get(b)));
                }
            }else {Collections.sort(stringList);}
            for (int i = 0; i<allProduct.size();i++) {
                boolean data = StringUtils.containsIgnoreCase(stringList.get(i), allProduct.get(i));
                if(!data){return false;}
            }
            return true;
        } else {
            stringList = allProduct;
            if(filters.equalsIgnoreCase("Price")) {
                for (int a = 0; a < stringList.size(); a++) {
                    if(StringUtils.containsIgnoreCase(stringList.get(a),"₹")){
                        stringList.set(a, stringList.get(a).replace("₹", ""));
                    } else if (StringUtils.containsIgnoreCase(stringList.get(a),"£")) {
                        stringList.set(a, stringList.get(a).replace("£", ""));
                    }else {stringList.set(a, stringList.get(a).replace("$", ""));}
                }
                for (String str : stringList) {
                    int number = Integer.parseInt(str.split("\\.")[0]);
                    integerList.add(number);
                }
                stringList.sort(Collections.reverseOrder());
                for (int b = 0; b < stringList.size(); b++) {
                    stringList.set(b,String.valueOf(integerList.get(b)));
                }
            }else {stringList.sort(Collections.reverseOrder());}
            for (int i = 0; i<allProduct.size();i++) {
                boolean data = StringUtils.containsIgnoreCase(stringList.get(i), allProduct.get(i));
                if(!data){return false;}
            }
            return true;
        }

    }
    public void changedRate(String rate)
    {
        elementHelper.clickBK(currency);
        genericUtils.timerr(50);
        switch (rate){
            case "Indian Rupee" -> elementHelper.clickBK(IndianRupee);
            case "Pound Sterling" -> elementHelper.clickBK(PoundSterling);
            case "US Dollar" -> elementHelper.clickBK(USDollar);
        }
    }
}
