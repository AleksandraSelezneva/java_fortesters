package ru.stqa.fortesters.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.fortesters.addressbook.model.GroupData;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testsGroupCreation() throws Exception {
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("test2", "test2", "test3");
        app.getGroupHelper().createGroup(group);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

        //добавляем в список before группу, которую только что создали
        before.add(group);

        //сравниваем объекты типа GroupData
        // Comparator<? super GroupData> byId = (o1, o2) -> Integer.compare(o1.getId(),o2.getId());
        // вставим тело функции (правая часть выражения) вместо (byID)
        //поиск max объекта, сравнение при помощи компаратора, получение id max объекта

        //устанавлваем для создаваемой группы id, полученный путем сравнения объектов о1 и о2 по id и выбора max
        group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));

        app.getSessionHelper().logout();
    }
}
