package stepDefinitions;

import Utils.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.LoginPage;

import java.util.List;

public class LoginStepdefs {
    LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    @Given("Kullanıcı web sayfasına erişir")
    public void DogruWebSayfasi() {
        Assert.assertTrue(loginPage.home());
    }

    @When("Kullanıcı {string} buttonuna tıklar")
    public void kullanıcıButtonunaTıklar(String arg0) {
        loginPage.ClickButton(arg0);
    }

    @Then("Hesaba erişin ve siparişleri yönetin popup açılır")
    public void hesabaErisinVeSiparisleriYonetinPopupAcılır() {
        Assert.assertTrue(loginPage.hesabaErisinVeSiparisleriYonetin());
    }

    @Then("{string} sayfası açılır")
    public void sayfasıAcılır(String arg0) {
        Assert.assertTrue(loginPage.CurrentPage(arg0));
    }

    @And("Kullanıcı gerekli bilgileri doldurur")
    public void kullanıcıGerekliBilgileriDoldurur(DataTable table) {
        List<List<String>> Obj = table.asLists();
        loginPage.EmptyField(Obj);
    }

    @And("Kullanıcı gizlilik politikasının kutusu şeçer")
    public void kullanıcıGizlilikPolitikasınıVeDevamButtonunuTıklar() {
        loginPage.clickCheckBox();
    }

    @And("Kullanıcı devam etmek için {string} buttonuna tıklar")
    public void KullanıcıDevamEtmekIçinButtonunaTıklar(String arg) {
        loginPage.clickButton(arg);
    }

    @Then("Kullanıcı başarlı bir şekilde kayıt olur.")
    public void kullanıcıBasarlıBirSekildeKayıtOlur() {
        Assert.assertTrue(loginPage.CreatedAccount());
    }

    @And("Kullanıcı {string} ve {string} bilgilerini yazar")
    public void kullanıcıEMailVePasswordIleYazar(String arg0,String arg1) {
        loginPage.EmailAndPassword(arg0,arg1);
    }

    @Then("Kullanıcı başarılı bir şekilde giriş yapar")
    public void kullanıcıBasarılıBirSekildeGirisYapar() {
        Assert.assertTrue(loginPage.LoginSuccessful());
    }

    @Then("Kullanıcı giriş yapamaz ve uyarı mesajı alır")
    public void kullanıcıGirisYapamazVeUyarıMesajıAlır() {
        Assert.assertTrue(loginPage.NotLoginProcess());
    }

}
