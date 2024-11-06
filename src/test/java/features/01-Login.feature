@all
Feature: Üye olma işlemi
  Background:
    Given Kullanıcı web sayfasına erişir

  @SIn
  Scenario: Kullanıcının başırlılı bir şekilde üye olması
    Given Kullanıcı "Sing In" buttonuna tıklar
    Then Hesaba erişin ve siparişleri yönetin popup açılır
    And Kullanıcı "Register" buttonuna tıklar
    Then "Register" sayfası açılır
    And Kullanıcı gerekli bilgileri doldurur
      | First Name | Last Name | E-Mail | Telephone | Password |
    And Kullanıcı gizlilik politikasının kutusu şeçer
    And Kullanıcı devam etmek için "Continue" buttonuna tıklar
    Then Kullanıcı başarlı bir şekilde kayıt olur.

  @LIn
  Scenario: Kullanıcını başırlılı bir şekilde giriş yapması
    Given Kullanıcı "Sing In" buttonuna tıklar
    Then Hesaba erişin ve siparişleri yönetin popup açılır
    And Kullanıcı "Login" buttonuna tıklar
    Then "Login" sayfası açılır
    And Kullanıcı 'E-mail' ve 'password' bilgilerini yazar
    When Kullanıcı devam etmek için "Login" buttonuna tıklar
    Then Kullanıcı başarılı bir şekilde giriş yapar

  @NoLIn
  Scenario: Kullanıcının yanlış bir şekilde giriş yapması
    Given Kullanıcı "Sing In" buttonuna tıklar
    Then Hesaba erişin ve siparişleri yönetin popup açılır
    And Kullanıcı "Login" buttonuna tıklar
    Then "Login" sayfası açılır
    And Kullanıcı 'DemoMail' ve 'password1' bilgilerini yazar
    When Kullanıcı devam etmek için "Login" buttonuna tıklar
    Then Kullanıcı giriş yapamaz ve uyarı mesajı alır