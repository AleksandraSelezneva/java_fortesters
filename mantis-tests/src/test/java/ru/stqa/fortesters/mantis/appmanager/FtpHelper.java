package ru.stqa.fortesters.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

    private final ApplicationManager app;
    private FTPClient ftp;

    //при вызове конструктора выполняется инициализация, создается ftp-клиент,
    //который будет передавать файты, устанавливать соединение и т.п.
    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient();
    }
//метод загружает новый файл, а старый временно переименовывает
    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));//соединение с сервером
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));//логин
        ftp.deleteFile(backup); //удаление предыдущей резервной копии
        ftp.rename(target, backup); //переименовываем удаленный файл и делаем его резервную копию
        ftp.enterLocalPassiveMode(); //пассивный режим передачи данных
        ftp.storeFile(target, new FileInputStream(file)); //передается файл
        ftp.disconnect();
    }
//метод восстанавливает старый файл
    public void restore(String backup, String target) throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.deleteFile(target); //удаление загруженного файла
        ftp.rename(backup, target); //восстановление оригинального файла из резервной копии
        ftp.disconnect();
    }
}
