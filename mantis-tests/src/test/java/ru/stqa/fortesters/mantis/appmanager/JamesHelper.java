package ru.stqa.fortesters.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.fortesters.mantis.model.MailMessage;
import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

    private ApplicationManager app;

    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    private Session mailSession;
    private Store store;
    private String mailserver;

    //создание телнет-клиента
    public JamesHelper(ApplicationManager app) {
        this.app = app;
        telnet = new TelnetClient();
        mailSession = Session.getDefaultInstance(System.getProperties());  //создание почтовой сессии
    }

    public boolean doesUserExist(String name) {
        initTelnetSession();
        write("verify " + name);
        String result = readUntil("exist");
        closeTelnetSession();
        return result.trim().equals("User" + name + " exist");
    }

    public void createUser(String name, String passwd) {
        initTelnetSession();  //установка соединения по протоколу телнет
        write("adduser " + name + " " + passwd); //пишем команду
        String result = readUntil("User " + name + " added"); //ждем пока на консоли появится такой текст
        closeTelnetSession();  //разрыв соединения
    }

    public void deleteUser(String name) {
        initTelnetSession();
        write ("deluser " + name);
        String result = readUntil("User " + name + " deleted");
        closeTelnetSession();
    }

    private void initTelnetSession() {
        //поучаем данные для входа
        mailserver = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminlogin");
        String password = app.getProperty("mailserver.adminpassword");

        try {
            telnet.connect(mailserver, port); //устанавливаем соединение с почтовым сервером
            in = telnet.getInputStream();  //берем входной поток
            out = new PrintStream(telnet.getOutputStream());  //берем выходной поток
        } catch (Exception e) {
            e.printStackTrace();
        }

        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write("");

        readUntil("Login id:");
        write(login);
        readUntil("Password:");
        write(password);

        readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    private String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write (String value) {
        try {
            out.println(value);
            out.flush();
            System.out.print(value);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

public void closeTelnetSession () {
        write ("quit");
}

//удаление всех писем из конкретного почтового ящика
public void drainEmail (String username, String password) throws MessagingException {
        Folder inbox = openInbox (username, password);
        for (Message message : inbox.getMessages()) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        closeFolder (inbox);
}

private void closeFolder (Folder folder) throws MessagingException {
        folder.close(true);
        store.close();
}

private Folder openInbox (String username, String password) throws MessagingException {
        store = mailSession.getStore("pop3"); //берем почтовую сессию, сообщаем желаемый протокол поо3
        store.connect(mailserver, username, password);  //установление соедимнения про этому протоколу
        Folder folder = store.getDefaultFolder().getFolder("INBOX");  //поучение доступа к папке входящих
        folder.open(Folder.READ_WRITE);  //открываем папку на чтение и запись
        return folder;
}

public List<MailMessage> waitForMail (String username, String password, long timeout) throws MessagingException {
  long now= System.currentTimeMillis();
    while (System.currentTimeMillis() < now + timeout) {
        List<MailMessage> allMail = getAllMail (username, password);
        if (allMail.size() > 0) {
            return allMail;
        }
        try {
            Thread.sleep(15000);
            //Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    throw new Error("No mail: (");
}

//извлечение всех писем и превращение их в модельные объекты
public List<MailMessage> getAllMail (String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password);
    List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
            closeFolder (inbox);
    return messages;
}
//построение модельных объектов
    public static MailMessage toModelMail(Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

