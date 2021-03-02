package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().list().size() == 1) {
            app.contact().create(new ContactData()
                    .withFirstname("Aleksandra").withLastname("Selezneva").withMobile("89217775533").withEmail("pochta").withGroup("test1"), true);
        }
    }

    @Test (enabled = true)
    public void testContactModification() throws Exception {
        app.goTo().homePage();
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        ContactData contact = new ContactData()
                .withId(before.get(index).getId()).withFirstname("Alex").withLastname("Selezneva").withMobile("89217775533").withEmail("didaf@icloud.com");
        app.contact().modify(index, contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());
        before.remove(before.size() - 1);
        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
        //app.getSessionHelper().logout();
    }
}
