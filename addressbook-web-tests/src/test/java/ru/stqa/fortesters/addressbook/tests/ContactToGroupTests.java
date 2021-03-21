package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.GroupData;

public class ContactToGroupTests extends TestBase {

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
        if (app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }
    @Test
    public void testContactsToGroup() throws Exception {
        ContactData contact = new ContactData();
        app.goTo().homePage();
        app.contact().addContactToGroup(contact);
    }
}
