package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(MainPage.class);
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOpen() {
        By homeIconLocator = By.className("home");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(homeIconLocator));
            return true;
        } catch (TimeoutException err){
            return false;
        }
    }

    private void clickPlusButton(){
        logger.debug("Going to click plus button");
        By plusLocator = By.className("circle");
        int i=0;
        for (; i< 25;i++){
            try {
                driver.findElement(plusLocator).click();
                break;
            } catch (NoSuchElementException | ElementClickInterceptedException |StaleElementReferenceException err){
                try {
                    logger.fatal(err.getMessage());
                    System.out.println(i);
                    Thread.sleep(200);
                } catch (InterruptedException ex){}
            }
        }
        if (i==20){
            throw new TimeoutException("XX");
        }
    }
    private WebElement getNewPlaylistItem(){
        return driver.findElement(By.xpath("//*[text()='New Playlist']"));
    }

    private WebElement getNewPlaylistField(){
        return driver.findElement(By.xpath("//*[@class='create']/input"));
    }
    public String createPlaylist(String playlistName){
        logger.info("starting to create new playlist with name = "+playlistName);
        clickPlusButton();
        getNewPlaylistItem().click();
        getNewPlaylistField().sendKeys(playlistName);
        getNewPlaylistField().sendKeys(Keys.RETURN);

        By greenBannerLocator = By.className("success");
        wait.until(ExpectedConditions.visibilityOfElementLocated(greenBannerLocator));

        String playlistId  =driver.getCurrentUrl().split("/")[5];
        logger.warn("Created playlistId = "+playlistId);
        return playlistId;
    }

    private WebElement getPlaylist(String playlistId){
        By playlistLocator = By.xpath("//*[@href='#!/playlist/"+playlistId+"']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(playlistLocator));
        return driver.findElement(playlistLocator);
    }
    public boolean playlistExist(String playlistId, String playlistName) {
        try{
            return getPlaylist(playlistId).isDisplayed() && getPlaylist(playlistId).getText().equals(playlistName);
        } catch (NoSuchElementException err) {
            return false;
        }
    }

    public void renamePlaylist(String playlistId, String newName) {
        WebElement playlist = getPlaylist(playlistId);

        JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView();", playlist);

        Actions actions = new Actions(driver);
        actions.doubleClick(playlist).perform();

        getEditField().sendKeys(Keys.CONTROL+"A");
        getEditField().sendKeys(newName);
        getEditField().sendKeys(Keys.ENTER);

        By secondGreenBanner = By.xpath("//*[@class='success show' and contains(text(),'"+newName+"')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(secondGreenBanner));
    }

    private WebElement getEditField() {
        By editFieldLocator = By.xpath("//*[@class='playlist playlist editing']/input");
        wait.until(ExpectedConditions.elementToBeClickable(editFieldLocator));
        return driver.findElement(editFieldLocator);
    }
}
