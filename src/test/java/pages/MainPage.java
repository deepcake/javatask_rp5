package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {


    public static String URL = "http://rp5.ru/";


    WebDriver driver;

    WebElement searchStr;
    WebElement pointNavi;

    @FindBy(id = "geo-vidget-close")
    WebElement geoVidgetCloseBtn;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        driver.get(URL);
    }

    public MainPage closeGeoWidget() {
        geoVidgetCloseBtn.click();
        return this;
    }

    public MainPage search(String q, String ac) {
        searchStr.sendKeys(q);
        driver.findElement(By.xpath("//*[@class='ac_results']/ul/li[contains(text(), '" + ac + "')]")).click();
        return this;
    }

    public String getPoint() {
        return pointNavi.getText();
    }


}
