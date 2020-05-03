import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.*;

public class UIB11_VerifyTable {
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
    public void verifyTable() {
        driver.get(baseUrl);

        WebElement lnkTable = driver.findElement(By.xpath("//*[text()='Sortable Data Tables']"));

        //Click on Sortable Data Tables and verify the header title
        lnkTable.isDisplayed();
        lnkTable.click();
        assertTrue(driver.findElement(By.xpath("//*[text()='Data Tables']")).isDisplayed());

        //Verify header at column 3 on Table 1
        String headerColumn3= driver.findElements(By.xpath("//table[@id='table1']//tr[1]//th[3]")).get(0).getText();
        assertEquals(headerColumn3, "Email");

        //Verify cell value (row 3, column 2) on Table 1
        assertEquals(driver.findElements(By.xpath("//table[@id='table1']/tbody//tr[3]//td[2]")).get(0).getText(), "Jason");

        //Verify cell value (row 2, column 4) on Table 1
        assertEquals(driver.findElements(By.xpath("//table[@id='table1']/tbody//tr[2]//td[4]")).get(0).getText(), "$51.00");

        //Click on Email header column on Table 2 and Verify the Email column sort by alphabetical from A-Z
        driver.findElement(By.xpath("//table[@id='table2']//tr[1]//th/span[text()='Email']")).click();

        //'To locate table'
        WebElement Table = driver.findElement(By.xpath("//table[@id='table2']/tbody"));

        //'To locate rows of table it will Capture all the rows available in the table
        List<WebElement> Rows = Table.findElements(By.tagName("tr"));

        //'List of email that sorting by Alphabet'
        ArrayList<String> sortedEmailList = new ArrayList<String>(Arrays.asList("fbach@yahoo.com", "jdoe@hotmail.com", "jsmith@gmail.com", "tconway@earthlink.net"));

        //'List of email after sorting on table'
        ArrayList<String> actualEmailList = new ArrayList();
        for (int i = 1; i <= Rows.size(); i++) {
            String value = driver.findElement(By.xpath("//table[@id='table2']/tbody/tr["+i+"]/td[3]")).getText();
            actualEmailList.add(value);
        }

        assertEquals(actualEmailList, sortedEmailList);

    }
}
