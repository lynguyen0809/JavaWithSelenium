import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class UIB03_verifyDropdown {
    private WebDriver driver;
    private String baseUrl;
    private SoftAssertions softly = new SoftAssertions();

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        baseUrl = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @AfterTest
    public void tearDown() {
        softly.assertAll();
        driver.quit();
    }

    @Test
    public void verifyDropdown() {
        driver.get(baseUrl);

        WebElement lnkDropdown = driver.findElement(By.linkText("Dropdown"));
        lnkDropdown.isDisplayed();
        lnkDropdown.click();

        // Select option by Label
        WebElement lblOption2 = driver.findElement(By.xpath("//*[@id='dropdown']/option[text() = 'Option 2']"));
        lblOption2.click();
        assertTrue(lblOption2.isSelected());

        // Select option by Index
        WebElement indexOpt1 = driver.findElement(By.xpath("//*[@id='dropdown']/option[2]"));
        indexOpt1.click();
        assertTrue(indexOpt1.isSelected());
        System.out.println(indexOpt1.isSelected());

        WebElement indexOpt2 = driver.findElement(By.xpath("//*[@id='dropdown']/option[3]"));
        indexOpt2.click();
        assertTrue(indexOpt2.isSelected());
    }
}
