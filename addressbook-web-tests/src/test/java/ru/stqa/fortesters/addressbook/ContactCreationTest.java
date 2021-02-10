package ru.stqa.fortesters.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    initContactCreation();
    fillContactForm(new ContactData("Aleksandra", "Selezneva", "89217775533", "didaf@icloud.com"));
    submitContactCreation();
    gotoHomePage();
    logout();
  }
}
