package ru.stqa.fortesters.mantis.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests  extends TestBase {


    @Test
    public void testChangePassword() throws IOException {
        HttpSession session = app.newSession();
        session.login ("administrator", "root");
        click(By.linkText("groups"));
        //app.goTo().homePage();

    }

}
