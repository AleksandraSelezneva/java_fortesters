package ru.stqa.fortesters.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.fortesters.mantis.model.UserData;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) {
        super(app);  //вызов конструктора базового класса, туда передается ссылка на ApplicationManager
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");  //откраваем нужную страницу браузера
        type(By.name("username"),username);
        type(By.name("email"),email);
        click(By.cssSelector("input[value='Signup']"));
    }

    public void finish(String conformationLink, String user, String password) {
        wd.get(conformationLink); //переходим по ссылке получения пароля
        type(By.name("realname"), user); //заполняем 1ое поле
        type(By.name("password"), password); //заполняем 1ое поле
        type(By.name("password_confirm"), password);//заполняем 2ое поле
        click(By.cssSelector("span.bigger-110"));
    }

}
