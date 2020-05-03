import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;

public class UIB06_verifyAlerts {
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
    public void verifyAlerts() throws InterruptedException {
        driver.get(baseUrl);

        WebElement lnkAlerts = driver.findElement(By.xpath("//*[text()='JavaScript Alerts']"));
        lnkAlerts.isDisplayed();
        lnkAlerts.click();

        //Verify header title is displayed
        assertTrue(driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).isDisplayed());

        // Verify the alert message is displayed
        WebElement btn1 = driver.findElement(By.xpath("//button[text()=\"Click for JS Alert\"]"));
        btn1.click();
        Alert alert1 = driver.switchTo().alert();
        assertEquals(alert1.getText(), "I am a JS Alert");

        // Verify the result message is returned correctly
        alert1.accept();
        WebElement msgResult = driver.findElement(By.xpath("//*[@id=\"result\"]"));
        assertEquals(msgResult.getText(), "You successfuly clicked an alert");

        //Verify the result message when canceling the alert
        driver.findElement(By.xpath("//button[text()=\"Click for JS Confirm\"]")).click();
        driver.switchTo().alert().dismiss();
        assertEquals(driver.findElement(By.xpath("//*[@id=\"result\"]")).getText(), "You clicked: Cancel");

        // Verify the entered value on alert
        driver.findElement(By.xpath("//button[text()=\"Click for JS Prompt\"]")).click();
        driver.switchTo().alert().sendKeys("Hello");
        driver.switchTo().alert().accept();
        assertEquals(driver.findElement(By.xpath("//*[@id=\"result\"]")).getText(), "You entered: Hello");
    }
}
