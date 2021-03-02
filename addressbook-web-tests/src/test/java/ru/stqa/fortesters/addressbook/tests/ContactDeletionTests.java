package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Aleksandra").withLastname("Selezneva").withMobile("89217775533").withEmail("pochta").withGroup("test1"), true);
    }
  }

  @Test (enabled = true)
  public void testContactDeletion() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    int index = 0;
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(0);
    Assert.assertEquals(before, after);
    //app.getSessionHelper().logout();
  }
}
