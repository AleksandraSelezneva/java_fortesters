package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstname("Aleksandra").withLastname("Selezneva").withMobile("89217775533").withEmail(
                            "pochta").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().homePage();
        //загружаем мн-во контактов
        ContactData contact = app.contact().all().iterator().next();
        //выбираем контакт, случайным образом
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    }
}
