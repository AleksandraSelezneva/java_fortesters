package ru.stqa.fortesters.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.fortesters.addressbook.model.ContactData;
import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super (wd);
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("mobile"),contactData.getMobile());
        type(By.name("email"),contactData.getEmail());
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }
    public void selectedContact() {
        click(By.name("selected[]"));
    }
    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
        assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    }
    public void initContactModification(){
        click(By.xpath("//img[@alt='Edit']"));
    }
    public void submitContactModification(){
        click(By.name("update"));
    }
}
