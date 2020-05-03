import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;

public class UIB09_VerifySlider {
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
    public void verifySlider() {
        driver.get(baseUrl);

        WebElement lnkHorizontalSlider = driver.findElement(By.xpath("//*[text()='Horizontal Slider']"));
        WebElement slider = driver.findElement(By.xpath("//*[@class='sliderContainer']/input"));
        WebElement sliderValue = driver.findElement(By.xpath("//*[@class='sliderContainer']/span"));

        //Select Slider Link and verify the header title
        lnkHorizontalSlider.isDisplayed();
        lnkHorizontalSlider.click();
        assertTrue(driver.findElement(By.xpath("//*[@id='content']/div/h3")).isDisplayed());

        //Set slider to 1
        int setValue1 = 1;
        ResetSlider(sliderValue, slider);
        MoveSlider(setValue1, sliderValue,slider);

        //Set slider to 2.5
        double setValue2 = 2.5;
        ResetSlider(sliderValue, slider);
        MoveSlider(setValue2, sliderValue, slider);

        //Set slider to 4.5
        double setValue3 = 4.5;
        ResetSlider(sliderValue, slider);
        MoveSlider(setValue3, sliderValue, slider);
    }
    // Move slide function
    public void MoveSlider(double setValue, WebElement sliderValue, WebElement slider){
        for (int i = 0; i < (setValue * 2); i++) {
            slider.sendKeys(Keys.chord(Keys.ARROW_RIGHT));
        }
        Double currValue = Double.valueOf(sliderValue.getText());
        assertEquals(currValue, setValue);
    }
    // Reset slide function
    public void ResetSlider(WebElement sliderValue, WebElement slider){

        Float numToMove = Float.valueOf(sliderValue.getText())*2;

        while (numToMove > 0) {
            slider.sendKeys(Keys.chord(Keys.ARROW_LEFT));
            numToMove--;
        }
        assertEquals(sliderValue.getText(), "0" );
    }
}
