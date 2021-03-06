// Generated by Selenium IDE
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertFalse;

public class UIB01_verifyCheckbox {
    private WebDriver driver;
    private String baseUrl;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        baseUrl = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void verifyCheckboxes() throws InterruptedException {
        driver.get(baseUrl);

        WebElement lnkCheckboxes = driver.findElement(By.linkText("Checkboxes"));
        lnkCheckboxes.isDisplayed();
        lnkCheckboxes.click();

        WebElement cbxCheckbox1 = driver.findElement(By.xpath("//*[@id='checkboxes']/input[1]"));
        WebElement cbxCheckbox2 = driver.findElement(By.xpath("//*[@id='checkboxes']/input[2]"));

        cbxCheckbox1.click();
        cbxCheckbox1.isSelected();

        if (cbxCheckbox2.isSelected()){
            cbxCheckbox2.click(); }
        assertFalse(cbxCheckbox2.isSelected());
    }
}
