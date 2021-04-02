package ru.stqa.fortesters.addressbook.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.stqa.fortesters.addressbook.model.Issue;

import java.io.IOException;
import java.util.Set;

import static com.google.gson.JsonParser.parseString;

public class RestHelper {

    private ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Issue> getIssues() throws IOException { //получаем список всех ишью в формате json
        String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
                .returnContent().asString();
        JsonElement parsed = parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    public Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt(); //возвращаем айди созданного ишью
    }
}
