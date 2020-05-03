import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;

public class UIB05_verifyFrames {
    private WebDriver driver;
    private String baseUrl;
    private SoftAssertions softly = new SoftAssertions();

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/chromedriver.exe");
        driver = new FirefoxDriver();

        baseUrl = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @AfterTest
    public void tearDown() {
        softly.assertAll();
        driver.quit();
    }

    @Test
    public void verifyFrames() throws InterruptedException {
        driver.get(baseUrl);

        WebElement lnkEditor = driver.findElement(By.xpath("//*[text()='WYSIWYG Editor']"));
        lnkEditor.isDisplayed();
        lnkEditor.click();
        Thread.sleep(5000);

        driver.switchTo().frame("mce_0_ifr");
        WebElement textArea = driver.findElement(By.xpath("//*[@id=\"tinymce\"]"));
        assertEquals(textArea.getText(), "Your content goes here.");

        textArea.click();
        textArea.clear();
        textArea.sendKeys("Hello, how are you?");
        assertEquals(textArea.getText(), "Hello, how are you?");
    }
}
