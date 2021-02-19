package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.fortesters.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testsGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    app.getSessionHelper().logout();
  }

}
