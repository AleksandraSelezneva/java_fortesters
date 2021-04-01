package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import ru.stqa.fortesters.addressbook.model.Groups;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() throws IOException {
        skipIfNotFixed(824);
        Groups groups = app.db().groups();
        //File photo = new File("src/test/resources/ava.png");
        ContactData newContact = new ContactData()
                .withFirstname("Aleksandra").withLastname("Selezneva")
                .withHome("123").withMobile("89217775533").withWork("12345")
                .withEmail("pochta").withEmail2("pochta2").withEmail3("pochta3")
                .withAddress("St.Petersburg, Popova st., 5")
                .inGroup(groups.iterator().next());
        //.withPhoto
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(newContact, true);
        }
    }

    @Test(enabled = true)
    public void testContactDeletion() throws Exception {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.goTo().homePage();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUI();
    }
}
