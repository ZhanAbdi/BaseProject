package pageObjectTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPageFactory;
import pageObjects.MainPage;

public class LoginTestFactory extends BaseTest{
    @Test
    public void loginToAppFactory_correctCredentials_loggedToApp(){
        LoginPageFactory loginPage = new LoginPageFactory(driver);
        loginPage.open(url);
        MainPage mainPage = loginPage.loginToApp(username,password);
        Assert.assertTrue(mainPage.isOpen());
    }

    @Test
    public void loginToAppFactory_incorrectCredentials_redFrame(){
        LoginPageFactory loginPage = new LoginPageFactory(driver);
        loginPage.open(url);
        loginPage.loginToApp(username,"wrongPassword");
        Assert.assertTrue(loginPage.isRedFrame());
    }

}
