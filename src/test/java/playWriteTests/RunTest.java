package playWriteTests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.Test;

public class RunTest {
    @Test
    public void openPage(){
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(10000));
                Page page = browser.newPage();
                page.navigate("http://whatsmyuseragent.org/");
            }
        }
    }

