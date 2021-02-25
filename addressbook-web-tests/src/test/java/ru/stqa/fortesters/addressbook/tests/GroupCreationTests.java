package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.fortesters.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testsGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    //кол-во групп до добавленя группы
    int before = app.getGroupHelper().getGroupCount();
    app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
    //кол-во групп до добавленя группы
    int after = app.getGroupHelper().getGroupCount();
    //проверка добавления группы
    Assert.assertEquals(after, before + 1);
    app.getSessionHelper().logout();
  }

}
