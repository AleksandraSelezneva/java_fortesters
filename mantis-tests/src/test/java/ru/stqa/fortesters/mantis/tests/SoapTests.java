package ru.stqa.fortesters.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.fortesters.mantis.model.Issue;
import ru.stqa.fortesters.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size()); //кол-во проектов
        for (Project project : projects) {  //информация о проектах, а именно - имя
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects(); //сначала получим мн-во проектов
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue description").withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue); //создадим новое ишью
        assertEquals(issue.getSummary(), created.getSummary()); //сравним имеющийся объект ишью с созданным по саммари
    }
}
