package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import unit.BaseTest_a;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class Link_aExample extends BaseTest_a {

    @BeforeClass
    public void startDriver() throws InterruptedException {
        this.driver.get(BASE_URL);
        TimeUnit.MILLISECONDS.sleep(20);
    }

    @DataProvider(name = "testLink_a", parallel = true)
    private Object[][] testLinkHREF_a() throws ExecutionException, InterruptedException {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        List<String> HREF_URL_a = links.stream().map(element -> element.getAttribute("href")).collect(Collectors.toList());
        List<String> futureResult_a = new ArrayList<>();
        ExecutorService ex_links_a = Executors.newFixedThreadPool(HREF_URL_a.size());
        for (String element : HREF_URL_a) {
            if (element.contains("https://")) {
                CompletableFuture<String> future_a = CompletableFuture.supplyAsync(() -> {
                    String flag = "sex";
                    try {
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(element).openConnection();
                        httpsURLConnection.connect();
                        if (httpsURLConnection.getResponseCode() > 400) {
                            flag = "false";
                        } else {
                            flag = "true";
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                    return flag;
                }, ex_links_a);
                futureResult_a.add(future_a.get());
            } else System.out.println(element);
        }
        ex_links_a.shutdown();
        String expected = "true";
        Object[][] result_a = new Object[futureResult_a.size()][2];
        for (int i = 0; i < futureResult_a.size(); i++) {
            result_a[i][0] = futureResult_a.get(i);
            result_a[i][1] = expected;
        }
        ex_links_a.shutdownNow();
        return result_a;
    }


    @Test(dataProvider = "testLink_a")
    public void testAssertions_a(String flag, String expected) {
        assertEquals(flag, expected);
    }
}
