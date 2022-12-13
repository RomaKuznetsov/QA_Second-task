package example;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.AssertJUnit.assertEquals;

public class WebStorageTest {
    private final String BASE_URL = "https://lugang.ru";

    @Test
    public void test() throws InterruptedException {
        open(BASE_URL);
       executeJavaScript("localStorage.setItem('Hello', arguments[0])", "Есть шмот!");
       String hello = executeJavaScript("localStorage.getItem('Hello')");
       assertEquals(hello,"Есть шмот!");

    }
}