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

public class UIB04_verifyFileUpload {
    private WebDriver driver;
    private String baseUrl;
    private SoftAssertions softly = new SoftAssertions();

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        baseUrl = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    @AfterTest
    public void tearDown() {
        softly.assertAll();
        driver.quit();
    }

    @Test
    public void verifyFileUpload() throws InterruptedException {
        driver.get(baseUrl);

        WebElement lnkFileUpload = driver.findElement(By.linkText("File Upload"));
        lnkFileUpload.isDisplayed();
        lnkFileUpload.click();

        WebElement btnChooseFile = driver.findElement(By.xpath("//*[@id='file-upload']"));
        WebElement btnUpload = driver.findElement(By.xpath("//*[@id='file-submit']"));

        btnChooseFile.sendKeys("D:\\BBS\\Selenium Course\\Images\\testImage.png");
        System.out.println(btnChooseFile.getText());
        btnUpload.click();
        Thread.sleep(5000);

        WebElement uploadedFile = driver.findElement(By.xpath("//*[@id='uploaded-files']"));
        assertTrue(uploadedFile.isDisplayed());

        uploadedFile.getText();
        assertEquals(uploadedFile.getText(), "testImage.png");
//        assertTrue();
    }
}
