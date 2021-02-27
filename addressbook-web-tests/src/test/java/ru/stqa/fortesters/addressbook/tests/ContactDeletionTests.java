package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.GroupData;

import java.util.List;


public class ContactDeletionTests extends TestBase {

  @Test (enabled = false)
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Aleksandra", "Selezneva", "89217775533", "pochta", "test1"),true);
    }
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectedContact(0);
    app.getContactHelper().deleteSelectedContact();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(0);
    Assert.assertEquals(before, after);

    app.getSessionHelper().logout();
  }
}
