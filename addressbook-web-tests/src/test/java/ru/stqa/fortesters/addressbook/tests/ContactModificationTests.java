package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
        app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstname("Aleksandra").withLastname("Selezneva")
                    .withHome("123").withMobile("89217775533").withWork("12345")
                    .withEmail("pochta").withEmail2("pochta2").withEmail3("pochta3")
                    .withGroup("test1").withAddress("St.Petersburg, Popova st., 5"), true);
        }
    }

    @Test(enabled = true)
    public void testContactModification() throws Exception {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Alex").withLastname("Selezneva")
                .withHome("123").withMobile("89217775533").withWork("12345")
                .withEmail("didaf@icloud.com").withEmail2("pochta2").withEmail3("pochta3")
                .withAddress("St.Petersburg, Popova st., 5");
        app.goTo().homePage();
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size() ));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
