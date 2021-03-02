package ru.stqa.fortesters.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.fortesters.addressbook.model.GroupData;
import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void deleteSelectedGroups() {
        click(By.name("delete"));
    }

    public void selectGroup(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    public void create(GroupData group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupPage();
    }

    public void modify(int index, GroupData group) {
        selectGroup(index);
        initGroupModification();
        fillGroupForm(group);
        submitGroupModification();
        returnToGroupPage();
    }

    public void delete(int index) {
selectGroup(index);
deleteSelectedGroups();
returnToGroupPage();
    }

    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<GroupData> list() {
        //создадим список, который будем заполнять
        List<GroupData> groups = new ArrayList<GroupData>();
        //список нужно заполнить путем извлечения из элемнта span текста (название группы)
        //получаем список объектов типа WebElement; ищем все элементы, которые имеют тег span и класс group
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
//проходим по этим элементам в цикле и из каждого из них получаем text (имя группы)
        for (WebElement element : elements) {
            String name = element.getText();
            //ищем внутри одного эдемента другой (в element, который получили выше, ищем чекбокс input )
            //в нем берем аттрибут value
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            //создаем объект типа GroupData и добавляем созданный объект в список
            groups.add(new GroupData().withId(id).withName(name));
        }
        //возвращаем заполненный список
        return groups;
    }
}
