package pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPageFactory {
    private WebDriver driver;

    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver,5);
        PageFactory.initElements(factory,this);
    }

    @FindBy(css="[type='email']")
    private WebElement emailField;

    @FindBy(xpath = "//*[@type='password']")
    private WebElement passwordField;

    @FindBy(tagName = "button")
    private WebElement loginButton;

    @FindBy(className = "error")
    private WebElement errorFrame;

    public MainPage loginToApp(String username, String password){
        emailField.sendKeys(Keys.CONTROL+"A");
        emailField.sendKeys(username);
        passwordField.sendKeys(Keys.CONTROL+"A");
        passwordField.sendKeys(password);

        loginButton.click();
        return new MainPage(driver);
    }
    public void open(String url){
        this.driver.get(url);
    }

    public boolean isRedFrame() {
        try {
            return errorFrame.isDisplayed();
        } catch (TimeoutException err){
            return false;
        }
    }
}
