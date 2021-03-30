package ru.stqa.fortesters.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import org.testng.SkipException;
import ru.stqa.fortesters.mantis.model.Issue;
import ru.stqa.fortesters.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private ApplicationManager app;

    public SoapHelper (ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects () throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        //получить список проектов, к которым пользователь имеет доступ
        //массив проектов
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
return Arrays.asList(projects).stream()
        .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
        .collect(Collectors.toSet());
    }

    public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        MantisConnectPortType mc = new MantisConnectLocator()
                //.getMantisConnectPort(new URL("http://localhost:8080/mantisbt-2.25.0/api/soap/mantisconnect.php"));
        .getMantisConnectPort(new URL(app.getProperty("mantisUrl")));
        return mc;
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException{
        MantisConnectPortType mc = getMantisConnect(); //открываем соединение
        String [] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();  //создание объекта типа IssueData с нужной структурой для передачи его в метод удаленного интерфейса
        issueData.setSummary(issue.getSummary());  //заполняем объекта типа IssueData, саммари
        issueData.setDescription(issue.getDescription()); //заполняем объекта типа IssueData, описание
        //заполняем объекта типа IssueData, проект
        //ссылка на проект с айди и его названием; несовпадение типов, нужен объект типа BigInteger, строим его
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        //заполняем объекта типа IssueData, категория
        //берем первую папавшуюся категорию
        issueData.setCategory(categories [0]);
        //обратное преобразование, присваиваем айди сощданного ишью в перевенную issueId
        BigInteger issueId = mc.mc_issue_add(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueData);
        //создаем запрос гет, передаем в него айди только что созданного ишью и в ответ получаем объект типа IssueData
        IssueData createdIssueData = mc.mc_issue_get(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"), issueId);
        //преобразуем в тот модельный объект, который нам нужен
        return new Issue().withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                          .withName(createdIssueData.getProject().getName()));
    }
}
