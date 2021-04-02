package ru.stqa.fortesters.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static ru.stqa.fortesters.addressbook.tests.TestBase.app;

public class RestTests {
    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = app.rest().getIssues(); //получаем множство объектов типа Issue
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue"); //создаем новый объект
        int issueId = app.rest().createIssue(newIssue);  //метод создания нового ишью возвращает айди созданного ишью
        Set<Issue> newIssues = app.rest().getIssues();
        oldIssues.add(newIssue.withId(issueId)); // добавим в старое множество новый созданный объект
        assertEquals(newIssues, oldIssues);
    }
}
