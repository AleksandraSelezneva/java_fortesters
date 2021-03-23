package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import ru.stqa.fortesters.addressbook.model.GroupData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        //File photo = new File("src/test/resources/ava.png");
        ContactData newContact = new ContactData()
                .withFirstname("Aleksandra").withLastname("Selezneva")
                .withHome("123").withMobile("89217775533").withWork("12345")
                .withEmail("pochta").withEmail2("pochta2").withEmail3("pochta3")
                .withAddress("St.Petersburg, Popova st., 5");
        //.withPhoto
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(newContact, true);
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test(priority = 1)
    public void testContactsToGroup() throws Exception {
        Contacts before = app.db().contacts();
        app.goTo().homePage();
        app.contact().selectedContactById(before.iterator().next().getId());
        app.contact().addContactToGroup();
        Contacts after = app.db().contacts();
        assertThat(before.iterator().next(), equalTo(after.iterator().next()));
        verifyContactListInUI();
    }

    @Test(priority = 2)
    public void testContactsFromGroup() throws Exception {
        Contacts before = app.db().contacts();
        app.goTo().homePage();
        app.contact().selectedGroup(before);
        app.contact().removeContactFromGroup();
        Contacts after = app.db().contacts();
        assertThat(before.iterator().next(), equalTo(after.iterator().next()));
        verifyContactListInUI();
    }
}
