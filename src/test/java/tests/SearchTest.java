package tests;

import model.HubDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.MainPage;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchTest {


    @DataProvider(parallel = true)
    private static Object[][] getTestData() {
        return new Object[][] {
                { "Пенза", "Пензенская область", "Погода в Пензе" },
                { "Лас-Вегас", "Нью-Мексико", "Погода в Лас-Вегасе" },
        };
    }


    @BeforeMethod(alwaysRun = true)
    void beforeMethod() throws MalformedURLException {
        System.out.printf("thread[%s] started at %s\n",
                Thread.currentThread().getId(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }


    @Test(dataProvider = "getTestData")
    public void testSearch(String q, String ac, String expectedSearchResult) {
        String searchResult = new MainPage(HubDriver.get())
                .closeGeoWidget()
                .search(q, ac).getPoint();

        Assert.assertEquals(searchResult, expectedSearchResult);
    }


    @AfterMethod(alwaysRun = true)
    void afterMethod() {
        HubDriver.quit();
        System.out.printf("thread[%s] finished at %s\n",
                Thread.currentThread().getId(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }


}
