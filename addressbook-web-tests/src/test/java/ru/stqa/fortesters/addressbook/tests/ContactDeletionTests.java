package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.GroupData;


public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    int before = app.getContactHelper().getContactCount();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Aleksandra", "Selezneva", "89217775533", "pochta", "test1"),true);
    }
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectedContact(0);
    app.getContactHelper().deleteSelectedContact();
    app.getNavigationHelper().gotoHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before - 1);
    app.getSessionHelper().logout();
  }
}
