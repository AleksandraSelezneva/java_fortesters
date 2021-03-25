package ru.stqa.fortesters.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.fortesters.mantis.appmanager.HttpSession;
import ru.stqa.fortesters.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationJamesTests extends TestBase  {

    //@BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String user = String.format("user%s",now);
        String email = String.format("user%s@localhost",now);
        String password = "password";
        app.james().createUser(user,password); // создание пользователя
        app.registration().start(user, email);
        //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        List<MailMessage> mailMessages = app.james().waitForMail (user, password, 60000); // получаем письмо из внешнего почтового сервера
        String conformationLink = findConformationLink(mailMessages, email); //ссылка, полученная из письма
        app.registration().finish(conformationLink, user, password);
        assertTrue (app.newSession().login(user,password));
    }
    HttpSession session = app.newSession();

    private String findConformationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    //@AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }

}
