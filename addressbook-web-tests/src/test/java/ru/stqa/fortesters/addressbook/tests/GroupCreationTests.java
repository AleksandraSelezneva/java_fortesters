package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.fortesters.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testsGroupCreation() throws Exception {
    app.gotoGroupPage();
    app.initGroupCreation();
    app.fillGroupForm(new GroupData("test1", "test2", "test3"));
    app.submitGroupCreation();
    app.returnToGroupPage();
    app.logout();
  }

}
