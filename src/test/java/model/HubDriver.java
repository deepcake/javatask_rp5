package model;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class HubDriver {


    public static String HUB_URL = "http://localhost:4444/wd/hub";

    //java -jar selenium-server-standalone-3.6.0.jar -role hub
    //java -jar selenium-server-standalone-3.6.0.jar -role node -hub http://localhost:4444/grid/register


    static ReentrantLock lock = new ReentrantLock();
    static Map<Long, RemoteWebDriver> cache = new HashMap<>();


    static RemoteWebDriver buildDriver() {
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(HUB_URL), DesiredCapabilities.chrome());
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }


    public static RemoteWebDriver get() {
        try {
            lock.lock();
            return cache.computeIfAbsent(Thread.currentThread().getId(), k -> buildDriver());
        } finally {
            lock.unlock();
        }
    }

    public static void quit() {
        try {
            lock.lock();
            cache.computeIfPresent(Thread.currentThread().getId(), (k, v) -> { v.quit(); return null; });
        } finally {
            lock.unlock();
        }
    }

}
