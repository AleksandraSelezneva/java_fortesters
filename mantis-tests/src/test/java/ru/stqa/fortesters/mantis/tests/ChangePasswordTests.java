package ru.stqa.fortesters.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.fortesters.mantis.appmanager.HttpSession;
import ru.stqa.fortesters.mantis.model.MailMessage;
import ru.stqa.fortesters.mantis.model.UserData;
import ru.stqa.fortesters.mantis.model.Users;
import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException {
        HttpSession session = app.newSession();
        app.admin().login("administrator", "root");
        app.admin().manageUsers();
        Users users = app.db().users();
        UserData user = users.iterator().next();
        app.admin().reset(user);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String conformationLink = app.mail().findConformationLink(mailMessages, user.getEmail());
        String password = "newPassword";
        app.admin().finish(conformationLink, user, password);

        assertTrue(session.login(user.getUsername(), password));
        assertTrue(session.isLoggedInAs(user.getUsername()));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}


