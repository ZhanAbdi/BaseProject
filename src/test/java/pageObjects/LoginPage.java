package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage  extends BasePage{
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private WebElement getEmailField(){
        return driver.findElement(By.xpath("//*[@type='email']"));
    }
    private WebElement getPasswordField(){
        return driver.findElement(By.cssSelector("[type='password']"));
    }
    private WebElement getLoginButton(){
        return driver.findElement(By.tagName("button"));
    }

    public MainPage loginToApp(String username, String password){
        logger.info("Logging to app using un = "+username+" pw = "+password);
        getEmailField().sendKeys(Keys.CONTROL+"A");
        getEmailField().sendKeys(username);
        getPasswordField().sendKeys(Keys.CONTROL+"A");
        getPasswordField().sendKeys(password);
        getLoginButton().click();
        return new MainPage(driver);
    }
    public void open(String url){
        driver.get(url);
    }

    public boolean isRedFrame() {
        By redFrameLocator = By.cssSelector(".error");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(redFrameLocator));
            return true;
        } catch (TimeoutException err){
            return false;
        }
    }
}
