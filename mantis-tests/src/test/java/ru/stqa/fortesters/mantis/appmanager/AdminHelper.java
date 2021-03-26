package ru.stqa.fortesters.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.fortesters.mantis.model.UserData;

public class AdminHelper extends HelperBase{

    public AdminHelper (ApplicationManager app) {
        super(app);
    }

    public void finish(String confirmationLink, UserData user, String newPassword) {
        wd.get(confirmationLink);
        type(By.name("realname"), user.getRealname());
        type(By.name("password"), newPassword);
        type(By.name("password_confirm"), newPassword);
        click(By.cssSelector("span.bigger-110"));
    }

    public void start (UserData user){
        click(By.linkText("Manage"));
        click(By.linkText("Manage Users"));
        click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", user.getId())));
        click(By.linkText("Reset Password"));
    }

}
