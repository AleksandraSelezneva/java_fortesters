package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import ru.stqa.fortesters.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/ava.png");
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
        String line = reader.readLine();
        while (line != null){
            String [] split = line.split(";");
            list.add (new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1]).withEmail(split[2])});
            line = reader.readLine();
        }
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

    @Test (enabled = false)
    public void testCurrentDir (){
        File currentDir = new File (".");
        System.out.println(currentDir.getAbsolutePath());
        //убедимся, что файл существует
        File photo = new File("src/test/resources/ava.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}
