package ru.stqa.fortesters.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues(); //получаем множство объектов типа Issue
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue"); //создаем новый объект
        int issueId = createIssue(newIssue);  //метод создания нового ишью возвращает айди созданного ишью
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId)); // добавим в старое множество новый созданный объект
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException { //получаем список всех ишью в формате json
        String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json").asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = RestAssured.given()
                .parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post("https://bugify.stqa.ru/api/issues.json").asString();
                JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt(); //возвращаем айди созданного ишью
    }
}
