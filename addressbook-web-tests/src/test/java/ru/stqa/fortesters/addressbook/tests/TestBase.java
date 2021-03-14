package ru.stqa.fortesters.addressbook.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.fortesters.addressbook.appmanager.ApplicationManager;

import static org.openqa.selenium.remote.BrowserType.CHROME;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser",BrowserType.CHROME));
    protected WebDriver wd;

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }
}
