package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Aleksandra", "Selezneva", "89217775533", "pochta"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }
}
