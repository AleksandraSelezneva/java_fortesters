package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import java.util.Set;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().all().size() == 1) {
            app.contact().create(new ContactData()
                    .withFirstname("Aleksandra").withLastname("Selezneva").withMobile("89217775533").withEmail("pochta").withGroup("test1"), true);
        }
    }

    @Test (enabled = true)
    public void testContactModification() throws Exception {
        app.goTo().homePage();
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Alex").withLastname("Selezneva").withMobile("89217775533").withEmail("didaf@icloud.com");
        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());
        before.remove(modifiedContact);
        before.add(contact);

        Assert.assertEquals(before, after);
        //app.getSessionHelper().logout();
    }
}
