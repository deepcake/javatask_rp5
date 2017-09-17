package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

public class SearchTest {


    private WebDriver driver;


    @DataProvider
    private static Object[][] getTestData() {
        return new Object[][] {
                { "Пенза", "Пензенская область", "Погода в Пензе" },
                { "Лас-Вегас", "Нью-Мексико", "Погода в Лас-Вегасе" },
        };
    }


    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }


    @Test(dataProvider = "getTestData")
    public void testSearch(String q, String ac, String expectedSearchResult) {
        String searchResult = new MainPage(driver)
                .closeGeoWidget()
                .search(q, ac).getPoint();

        Assert.assertEquals(searchResult, expectedSearchResult);
    }


    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }


}
