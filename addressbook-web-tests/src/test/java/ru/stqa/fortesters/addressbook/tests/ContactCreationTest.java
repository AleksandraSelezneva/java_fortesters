package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import ru.stqa.fortesters.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

  @Test (enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    app.goTo().groupPage();
    if (!app.group().isThereAGroup()) {
      app.group().create(new GroupData().withName("test1").withFooter("test3"));
    }
    ContactData contact = new ContactData()
            .withFirstname("Aleksandra").withLastname("Selezneva").withMobile("89217775533").withEmail("pochta").withGroup("test1");
    app.contact().create(contact,true);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    //добавляем в список before группу, которую только что создали
    before.add(contact);

    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
    //app.getSessionHelper().logout();
  }
}
