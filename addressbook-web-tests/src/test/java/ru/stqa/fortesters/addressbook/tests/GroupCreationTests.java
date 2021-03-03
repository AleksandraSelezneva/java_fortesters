package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.fortesters.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test
    public void testsGroupCreation() throws Exception {
        app.goTo().groupPage();
        Set<GroupData> before = app.group().all();
        GroupData group = new GroupData().withName("test2");
        app.group().create(group);
        Set<GroupData> after = app.group().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        //добавляем в список before группу, которую только что создали
        before.add(group);
        Assert.assertEquals(before, after);
       // app.getSessionHelper().logout();
    }
}
