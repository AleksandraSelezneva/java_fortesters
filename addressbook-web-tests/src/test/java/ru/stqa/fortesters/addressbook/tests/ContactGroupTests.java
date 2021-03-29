package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.GroupData;
import ru.stqa.fortesters.addressbook.model.Groups;
import static org.testng.Assert.assertTrue;

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
        if (app.db().contactWithoutGroups().size() == 0) {
            app.goTo().homePage();
            app.contact().create(newContact, true);
        }
        if (app.db().contactWithGroups().size() == 0) {
            ContactData before = app.db().contactWithoutGroup();
            Groups groups = app.db().groups();
            GroupData group = groups.iterator().next();
            app.goTo().homePage();
            app.contact().selectContactWithoutGroup(before);
            app.contact().selectGroup(group);
            app.contact().addContactToGroup();
        }
    }

    @Test (priority = 1)
    public void testContactsToGroup() throws Exception {
        ContactData before = app.db().contactWithoutGroup();
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();
        app.goTo().homePage();
        app.contact().selectContactWithoutGroup(before);
        app.contact().selectGroup(group);
        app.contact().addContactToGroup();
        ContactData after = app.db().contactById(before.getId());
        assertTrue(after.getGroups().contains(group));
        verifyContactListInUI();
    }

    @Test(priority = 2)
    public void testContactsFromGroup() throws Exception {
        ContactData before = app.db().contactWithGroup();
        GroupData group = before.getGroups().iterator().next();
        app.goTo().homePage();
        app.contact().groupName(group);
        app.contact().selectContact(before);
        app.contact().removeContactFromGroup();
        ContactData after = app.db().contactById(before.getId());
        assertTrue(after.getGroups().contains(group));
        verifyContactListInUI();
    }
}
