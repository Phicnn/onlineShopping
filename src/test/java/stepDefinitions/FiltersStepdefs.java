package stepDefinitions;

import Utils.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.Filters;

public class FiltersStepdefs {
    Filters filters = new Filters(DriverFactory.getDriver());
    @Given("Kullanıcı üst katmanda bulunan seçeneklerden {string} şeçer")
    public void kullanıcıUstKatmandaBulunanSeceneklerdenSecer(String arg0) {
        filters.UstKatmandaBulunanSecenekler(arg0);
    }

    @When("Kullanıcı sol taraftaki şeçeneklerden {string} şeçer")
    public void kullanıcıSolTaraftakiSeceneklerdenSecer(String arg0) {
        filters.SolTaraftakiSecenekler(arg0);
    }

    @And("Kullanıcı {string} formatını şeçer ürünleri görmek için")
    public void kullanıcıFormatınıSecerUrunleriGormekIcin(String arg0) {
        filters.FormatUrunleriGormekIcin(arg0);
    }

    @And("Kullanıcı sıralama olarak {string} şeçer")
    public void kullanıcıSıralamaOlarakSecer(String arg0) {
        filters.ShortBy(arg0);
    }

    @Then("Ürünlerin {string} göre {} doğru sıralandığını emin olur")
    public void urunlerinGoreDogruSıralandıgınıEminOlur(String arg0,String arg1) {
        Assert.assertTrue(filters.CurrentSerial(arg0,arg1));
    }

    @And("Kullanıcı para birimi {string} olarak şeçer")
    public void kullanıcıParaBirimiOlarakSecer(String arg0) {
        filters.changedRate(arg0);
    }
}
