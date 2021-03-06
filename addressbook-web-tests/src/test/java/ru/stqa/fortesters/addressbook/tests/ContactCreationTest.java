package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import ru.stqa.fortesters.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @Test(enabled = true)
    public void testContactCreation() throws Exception {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.goTo().groupPage();
        if (!app.group().isThereAGroup()) {
            app.group().create(new GroupData().withName("test1").withFooter("test3"));
        }
        ContactData contact = new ContactData()
                .withFirstname("Aleksandra").withLastname("Selezneva")
                .withHome("123").withMobile("89217775533").withWork("12345")
                .withEmail("pochta").withEmail2("pochta2")
                .withGroup("test1").withAddress("St.Petersburg, Popova st., 5");
        app.contact().create(contact, true);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
        //app.getSessionHelper().logout();
    }
}
