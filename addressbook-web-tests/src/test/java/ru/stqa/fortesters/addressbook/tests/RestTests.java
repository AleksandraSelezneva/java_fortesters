package ru.stqa.fortesters.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.service.spi.ServiceException;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.fortesters.addressbook.model.Issue;

import java.io.IOException;
import java.util.Set;

import static com.google.gson.JsonParser.parseString;
import static org.testng.Assert.assertEquals;

public class RestTests {
    @Test
    public void testCreateIssue() throws IOException {
        skipIfNotFixed(824);
        Set<Issue> oldIssues = getIssues(); //получаем множство объектов типа Issue
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue"); //создаем новый объект
        int issueId = createIssue(newIssue);  //метод создания нового ишью возвращает айди созданного ишью
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId)); // добавим в старое множество новый созданный объект
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException { //получаем список всех ишью в формате json
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt(); //возвращаем айди созданного ишью
    }


    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
    public boolean isIssueOpen(int issueId) throws IOException, ServiceException {
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/" + String.format("issues/%s.json", issueId)))
                .returnContent().asString();
        JsonElement parsed = parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        JsonElement line = issues.getAsJsonArray().get(0);
        if (line.getAsJsonObject().get("state_name").getAsString().equals("Resolved") ||
                line.getAsJsonObject().get("state_name").getAsString().equals("Closed")) {
            return false;
        } else {
            return true;
        }
    }
}
