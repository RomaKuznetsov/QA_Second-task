package unit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected final String BASE_URL = "https://lugang.ru/";

    protected WebDriver driver;

    @BeforeClass
    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
        this.driver = new ChromeDriver();

    }

    @BeforeMethod
    public void startDriver() throws InterruptedException {
        this.driver.get(BASE_URL);
        TimeUnit.MILLISECONDS.sleep(20);
    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(20);
        this.driver.close();
    }
}

