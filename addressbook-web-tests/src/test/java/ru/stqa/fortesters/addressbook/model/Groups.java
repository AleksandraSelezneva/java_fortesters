package ru.stqa.fortesters.addressbook.model;

import com.google.common.collect.ForwardingSet;
import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {

    private Set<GroupData> delegate;

    public Groups(Groups groups) {
        this.delegate = new HashSet<GroupData>(groups.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<GroupData>();
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }
    public Groups withAdded(GroupData group) {
Groups groups = new Groups(this); //создана копия
groups.add(group);  //в эту копию добавляем объект, кот.передан в кач.параметра
return groups;  //возвращаем копию с добавленной группой
    }
// метод, кот.делает копию, из кот.удалена какая-то группа
    public Groups without(GroupData group) {
        Groups groups = new Groups(this); //создана копия
        groups.remove(group);  //из этой копии удаляем объект, кот. передан в кач. параметра
        return groups;  //возвращаем копию без группы
    }
}
