import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class UIB02_verifyDragAndDrop {
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
    public void verifyDragAndDrop() throws InterruptedException, IOException {
        try{
            driver.get(baseUrl);
            driver.manage().window().maximize();

            String basePath = new File("").getAbsolutePath();
            final String JQUERY_LOAD_SCRIPT = (basePath + "/src/test/resources/jquery_load_helper.js");
            String jQueryLoader = readFile(JQUERY_LOAD_SCRIPT);

            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeAsyncScript(
                    jQueryLoader /* , http://localhost:8080/jquery-1.7.2.js */);

            // ready to rock
            js.executeScript("jQuery(function($) { " + " $('input[name=\"q\"]').val('bada-bing').closest('form').submit(); "
                    + " }); ");

            String filePath = basePath + "/src/test/resources/drag_and_drop_helper.js";

            WebElement lnkDragAndDrop = driver.findElement(By.linkText("Drag and Drop"));
            lnkDragAndDrop.isDisplayed();
            lnkDragAndDrop.click();

            String fromColumA = "div#column-a";
            String toColumnB = "div#column-b";

            StringBuffer buffer = new StringBuffer();
            String line;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while((line = br.readLine())!=null)
                buffer.append(line);

            String javaScript = buffer.toString();

            javaScript = javaScript + "$('" + fromColumA + "').simulateDragDrop({ dropTarget: '" + toColumnB + "'});";
            ((JavascriptExecutor)driver).executeScript(javaScript);

            String textA = driver.findElement(By.xpath("//*[@id='column-a']")).getText();
            String textB = driver.findElement(By.xpath("//*[@id='column-b']")).getText();
            System.out.println(textA);
            System.out.println(textB);

            assertEquals(textA, "B");
            assertEquals(textB, "A");

            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String readFile(String file) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        FileInputStream stream = new FileInputStream(file);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            stream.close();
        }
    }
}
