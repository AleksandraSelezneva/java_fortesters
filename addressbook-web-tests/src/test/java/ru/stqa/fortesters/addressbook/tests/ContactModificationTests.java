package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactCreation() throws Exception {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().selectedContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Alex", "Selezneva", "89217775533", "didaf@icloud.com", null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomePage();
        app.getSessionHelper().logout();
    }
}
