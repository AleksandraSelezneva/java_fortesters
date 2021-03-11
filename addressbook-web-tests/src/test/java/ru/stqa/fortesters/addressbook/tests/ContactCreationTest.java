package ru.stqa.fortesters.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.Contacts;
import ru.stqa.fortesters.addressbook.model.GroupData;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/ava.png");
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null){
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        List <ContactData> contacts =  (List <ContactData>) xstream.fromXML(xml);
        return contacts.stream().map((g)->new Object[] {g}).collect(Collectors.toList()).iterator();
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
