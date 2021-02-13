package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.Test;


public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().selectedContact();
    app.getContactHelper().deleteSelectedContact();
    app.getNavigationHelper().gotoHomePage();
    app.getSessionHelper().logout();
  }
}
