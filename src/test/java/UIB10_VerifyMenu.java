import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;

public class UIB10_VerifyMenu {
    private WebDriver driver;
    private String baseUrl;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();

        baseUrl = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyMenu() throws InterruptedException {
        driver.get(baseUrl);
        WebElement lnkJMenus = driver.findElement(By.xpath("//*[text()='JQuery UI Menus']"));

        // Click on link JQuery UI Menus and verify header title
        lnkJMenus.isDisplayed();
        lnkJMenus.click();
        assertTrue(driver.findElement(By.xpath("//*[@id='content']/div/h3")).isDisplayed());

        //Select Enabled -> JQuery UI menu and verify JQuery UI header title is displayed
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[text()='Enabled']")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[text()='Back to JQuery UI']")));

        assertTrue(driver.findElement(By.xpath("//*[text()='JQuery UI']")).isDisplayed());

        //Click on Menu link and verify JQueryUI - Menu header title is displayed
        executor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//*[text()='Menu']")));
        assertTrue(driver.findElement(By.xpath("//*[text()='JQueryUI - Menu']")).isDisplayed());

        //Select Enabled -> Downloads -> CSV menu
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[text()='Enabled']")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[text()='Downloads']")));
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[text()='CSV']")));
        Thread.sleep(10000);

        //Verify The menu.csv file is saved to local successful
        String fileName = "menu.csv";
        String home = System.getProperty("user.home");
        File file = new File(home + "/Downloads/" + fileName);
        String downloadPath = home + "/Downloads/";

        isFileDownloaded(downloadPath, fileName);
        if (file.exists())
            file.delete();
    }
    // Check file is downloaded function
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        boolean flag = false;
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i++) {
            if (dir_contents[i].getName().equals(fileName))
                return flag = true;
        }
        return flag;
    }
}
