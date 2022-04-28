package pageObjectTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import pageObjects.MainPage;

public class LoginTest extends BaseTest{
    @Test
    public void loginToApp_correctCredentials_loggedToApp() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(url);
        MainPage mainPage = loginPage.loginToApp(username,password);
        Assert.assertTrue(mainPage.isOpen());
    }

    @Test
    public void loginToApp_incorrectCredentials_redFrame() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(url);
        loginPage.loginToApp(username,"wrongPassword");
        Assert.assertTrue(loginPage.isRedFrame());
    }

}
