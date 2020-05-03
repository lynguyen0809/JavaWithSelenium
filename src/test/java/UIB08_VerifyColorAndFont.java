import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class UIB08_VerifyColorAndFont {
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
    public void verifyColorAndFont(){
        driver.get(baseUrl);
        WebElement lnkChallengingDOM = driver.findElement(By.xpath("//*[text() = 'Challenging DOM']"));
        lnkChallengingDOM.isDisplayed();
        lnkChallengingDOM.click();

        //Verify header title is displayed
        assertTrue(driver.findElement(By.xpath("//*[@id='content']/div/h3")).isDisplayed());

        WebElement btn = driver.findElement(By.xpath("//*[@class = 'button']"));
        WebElement btnAlert = driver.findElement(By.xpath("//*[@class = 'button alert']"));
        WebElement btnSuccess = driver.findElement(By.xpath("//*[@class = 'button success']"));

        //Verify font size of btn
        String fontSize = btn.getCssValue("font-size");
        assertEquals(fontSize, "16px");

        //Verify background-color of btnAlert button
        //List of expected Background color in browsers
        ArrayList<String> listBackgroundColor = new ArrayList<String>(Arrays.asList("rgb(198, 15, 19)", "rgba(198, 15, 19, 1)", "#c60f13"));

        //Verify background color
        for(int i=0; i<listBackgroundColor.size(); i++){

            String _backgroundColor = btnAlert.getCssValue("background-color");
            if(_backgroundColor == listBackgroundColor.get(i)){
                assertEquals(_backgroundColor, listBackgroundColor.get(i));
            }
        }

        //Verify border-color of btnSuccess button
        //List of expected Border color in browsers
        ArrayList<String> listBorderColor = new ArrayList<String>(Arrays.asList("rgb(69, 122, 26)", "#457a1a"));

        //Verify border color
        for(int j=0; j<listBackgroundColor.size(); j++){

            String _borderColor = btnSuccess.getCssValue("border-color");

            if(_borderColor == listBorderColor.get(j)){
                assertEquals(_borderColor, listBorderColor.get(j));
            }
        }

    }
}
