package ru.stqa.fortesters.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.mantis.appmanager.HttpSession;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import static org.testng.Assert.assertTrue;

public class LoginTests  extends TestBase {

    @Test
    public void testLogin () throws IOException, ServiceException {
        skipIfNotFixed(1);
        HttpSession session = app.newSession();
        //проверка, что после входа в систему на странице появился нужный текст
        assertTrue(session.login ("administrator", "root"));
        //проверка, что залогирован администратор
        assertTrue(session.isLoggedInAs("administrator"));
    }
}
