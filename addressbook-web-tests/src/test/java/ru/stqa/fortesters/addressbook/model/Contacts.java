package ru.stqa.fortesters.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Contacts extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public Contacts(Contacts contacts) {
        this.delegate = new HashSet<ContactData>(contacts.delegate);
    }

    public Contacts() {
        this.delegate = new HashSet<ContactData>();
    }

    public Contacts(Collection<ContactData> contacts) {
        this.delegate = new HashSet<ContactData>(contacts);
    }

    @Override
    protected Set<ContactData> delegate() {
        return delegate;
    }
    public Contacts withAdded(ContactData contact) {
        Contacts contacts = new Contacts(this); //создана копия
        contacts.add(contact);  //в эту копию добавляем объект, кот.передан в кач.параметра
        return contacts;  //возвращаем копию с добавленной группой
    }

    // метод, кот.делает копию, из кот.удалена какая-то группа
    public Contacts without(ContactData contact) {
        Contacts contacts = new Contacts(this); //создана копия
        contacts.remove(contact);  //из этой копии удаляем объект, кот. передан в кач. параметра
        return contacts;  //возвращаем копию без группы
    }
}
