package pageObjectTests;

import com.github.javafaker.Faker;
import enums.Browsers;
import helper.BrowserFabric;
import helper.Screenshot;
import helper.Token;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pageObjects.MainPage;


public class BaseTest {
    protected Faker faker;
    protected String username;
    protected String password;
    protected String token;
    protected String url;
    protected WebDriver driver;

    @Parameters({"username", "password"})
    @BeforeClass
    public void runOnStart(String username, String password){
        token = Token.get(username, password);
        System.out.println(token);
    }

    @Parameters({"url", "username", "password", "browser"})
    @BeforeMethod
    public void startUp(String url,String username, String password, String browser) {
        faker = new Faker();
        this.url = url;
        this.username=username;
        this.password=password;
        Browsers type = browser.equals("chrome") ? Browsers.CHROME : Browsers.FIREFOX;
        driver = BrowserFabric.getDriver(type);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if(result.getStatus()==ITestResult.FAILURE){
            Screenshot.take(driver,result.getName());
        }
        driver.quit();
    }

    public MainPage login(){
        driver.get(url);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("localStorage.setItem(arguments[0],arguments[1])","jwt-token","\""+token+"\"");
        driver.navigate().refresh();

        return new MainPage(driver);
    }
}
