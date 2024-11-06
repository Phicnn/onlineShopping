@all
Feature: Ürün filtreleme işlemi
  Background:
    Given Kullanıcı web sayfasına erişir
    When Kullanıcı "Sing In" buttonuna tıklar
    Then Hesaba erişin ve siparişleri yönetin popup açılır
    And Kullanıcı "Login" buttonuna tıklar
    And "Login" sayfası açılır
    And Kullanıcı 'E-mail' ve 'password' bilgilerini yazar
    When Kullanıcı devam etmek için "Login" buttonuna tıklar

  @filters
  Scenario:Kullanıcı şeçtigi kategoride filtrele işlemleri
    Given Kullanıcı üst katmanda bulunan seçeneklerden "Women" şeçer
    Then "Women" sayfası açılır
    When Kullanıcı sol taraftaki şeçeneklerden "Fashion" şeçer
    Then "Fashion" sayfası açılır
    And Kullanıcı "List" formatını şeçer ürünleri görmek için
    And Kullanıcı sıralama olarak "Name (Z - A)" şeçer
    Then Ürünlerin "Name" göre "Z - A" doğru sıralandığını emin olur
    Then Kullanıcı "Grid" formatını şeçer ürünleri görmek için
    And Kullanıcı sıralama olarak "Price (Low > High)" şeçer
    Then Ürünlerin "Price" göre "Low > High" doğru sıralandığını emin olur

  @filtersRate
  Scenario:Kullanıcı para birimini değiştirdikten sonraki filtrele işlemleri
    Given Kullanıcı üst katmanda bulunan seçeneklerden "Women" şeçer
    Then "Women" sayfası açılır
    When Kullanıcı sol taraftaki şeçeneklerden "Fashion" şeçer
    Then "Fashion" sayfası açılır
    And Kullanıcı para birimi "Pound Sterling" olarak şeçer
    And Kullanıcı "List" formatını şeçer ürünleri görmek için
    And Kullanıcı sıralama olarak "Name (A - Z)" şeçer
    Then Ürünlerin "Name" göre "A - Z" doğru sıralandığını emin olur
    Then Kullanıcı "Grid" formatını şeçer ürünleri görmek için
    And Kullanıcı sıralama olarak "Price (High > Low)" şeçer
    Then Ürünlerin "Price" göre "High > Low" doğru sıralandığını emin olur