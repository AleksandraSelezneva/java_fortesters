package ru.stqa.fortesters.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private CloseableHttpClient httpclient;
    private ApplicationManager app;

    //создается новая сессия, новый клиент
    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    //метод для выполнения логина
    public boolean login(String username, String password) throws IOException {
        //содание будущего запроса для действий на стронице логина, пока пустой
        //Post-запрос передает данные
        HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
        //формирование параметров для заполненя запроса
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        //упаковка параметров и помещение их в ранее созданный запрос post
        post.setEntity(new UrlEncodedFormEntity(params));
        //отправление запроса, в результате которого получаем ответ response
        CloseableHttpResponse response = httpclient.execute(post);
        //получаем текст ответа при помощи вспомогательной функции geTextFrom
        String body = geTextFrom(response);
        //проверка, действительно ли пользователь успешно вошел
        //дествительно ли код страницы содержит строку с именем пользователя
        //return body.contains
                //(String.format("<span class=\"label hidden-xs label-default arrowed\">%s</span>",
               // username));
        return body.contains(String.format("<span class=\"user-info\">%s</span>", username));
    }


    private String geTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

    //метод для определения, какой пользователь сейчас залогинен
    public boolean isLoggedInAs(String username) throws IOException {
        //формрование запроса для входа на главную страницу
        //Get-запрос не передает параметры
        HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");
        //выполняем запрос, получаем ответ
        CloseableHttpResponse response = httpclient.execute(get);
        //получаем текст ответа
        String body = geTextFrom(response);
        //проверяем наличие нужного фрагмента в тексте страницы
        return body.contains
                (String.format("<span class=\"label hidden-xs label-default arrowed\">%s</span>", username));
    }
}
