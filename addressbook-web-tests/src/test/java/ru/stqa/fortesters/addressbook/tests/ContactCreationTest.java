package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.GroupData;

public class ContactCreationTest extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("Aleksandra", "Selezneva", "89217775533", "pochta", "test1"),true);
    app.getNavigationHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }
}
