package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import unit.BaseTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class LinkExample extends BaseTest {


    @BeforeClass
    public void startDriver() throws InterruptedException {
        this.driver.get(BASE_URL);
        TimeUnit.MILLISECONDS.sleep(10);
    }

    @DataProvider(name = "testLink", parallel = true)
    private Object[][] testLinkHREF() throws ExecutionException, InterruptedException {
        List<WebElement> links = driver.findElements(By.tagName("link"));
        List<String> HREF_URL = links.stream().map(element -> element.getAttribute("href")).collect(Collectors.toList());
        List<String> futureResult = new ArrayList<>();
        ExecutorService ex_links = Executors.newFixedThreadPool(HREF_URL.size());
        for (String element : HREF_URL) {
//            if (element.contains("http://")) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                String flag = "sex";
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(element).openConnection();
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() > 400) {
                        flag = "false";
                    } else {
                        flag = "true";
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return flag;
            }, ex_links);
            futureResult.add(future.get());
//            } else System.out.println(element);
        }
        ex_links.shutdown();
        String expected = "true";
        Object[][] result = new Object[futureResult.size()][2];
        for (int i = 0; i < futureResult.size(); i++) {
            result[i][0] = futureResult.get(i);
            result[i][1] = expected;
        }
        ex_links.shutdownNow();
        return result;
    }

    @Test(dataProvider = "testLink")
    public void testAssertions(String flag, String expected) {
        assertEquals(flag, expected);

    }


}