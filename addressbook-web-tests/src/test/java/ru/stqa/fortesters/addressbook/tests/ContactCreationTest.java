package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import ru.stqa.fortesters.addressbook.model.GroupData;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/ava.png");
        list.add(new Object[]{new ContactData()
                .withFirstname("Aleksandra").withLastname("Selezneva")
                .withHome("123").withMobile("89217775533").withWork("12345")
                .withEmail("pochta").withEmail2("pochta2").withEmail3("pochta3")
                .withGroup("test1").withAddress("St.Petersburg, Popova st., 5")
                .withPhoto(photo)});
        list.add(new Object[]{new ContactData()
                .withFirstname("Konstantin").withLastname("Seleznev")
                .withHome("123").withMobile("89522220000").withWork("12345")
                .withEmail("post").withEmail2("post2").withEmail3("post3")
                .withGroup("test1").withAddress("St.Petersburg, Popova st., 5")
                .withPhoto(photo)});
        return list.listIterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.goTo().groupPage();
        if (!app.group().isThereAGroup()) {
            app.group().create(new GroupData().withName("test1").withFooter("test3"));
        }
        app.contact().create(contact, true);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
        //app.getSessionHelper().logout();
    }

    @Test (enabled = true)
    public void testCurrentDir (){
        File currentDir = new File (".");
        System.out.println(currentDir.getAbsolutePath());
        //убедимся, что файл существует
        File photo = new File("src/test/resources/ava.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}
