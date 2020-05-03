import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.Keys;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class UIB07_verifyKeyPresses {
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
    public void verifyKeyPresses(){
        driver.get(baseUrl);
        WebElement lnkAlerts = driver.findElement(By.xpath("//*[text()='Key Presses']"));
        lnkAlerts.isDisplayed();
        lnkAlerts.click();

        //Verify header title is displayed
        assertTrue(driver.findElement(By.xpath("//*[@id='content']/div/h3")).isDisplayed());
        
        //Press TAB and verify the result message
        driver.findElement(By.xpath("//input[@id='target']")).sendKeys(Keys.TAB);
        WebElement lblResultMsg = driver.findElement(By.xpath("//p[@id='result']"));
        assertEquals(lblResultMsg.getText(), "You entered: TAB");


        Actions actions = new Actions(driver);

        //Press ENTER and verify returned result
        actions.sendKeys(Keys.ENTER).build().perform();
        assertEquals(lblResultMsg.getText(), "You entered: ENTER");

        //Press G key ang verify the result message
        actions.keyDown(Keys.CONTROL).sendKeys("g").keyUp(Keys.CONTROL).perform();
        assertEquals(lblResultMsg.getText(), "You entered: G");
    }

}
