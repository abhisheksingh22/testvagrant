package com.testvagrant.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import com.testvagrant.util.SetProperties;

public class BaseUIConfiguration {

    private static int explicitWaitInSeconds;

    public static WebDriver setup_driver_and_open_browser(WebDriver webDriver) {
        String browser = SetProperties.env.getValue("browser");

        // Set up the driver as mentioned in 'env.properties' file and run against OS found
        try {
            /**************** Initiate Mozilla Firefox Browser. *************/
            if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
            }
            /**************** Initiate Google Chrome Browser *******************/
            else if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                webDriver = new ChromeDriver(options);
            } else {
                System.out.println("Driver (browser) not specified");
            }
        } catch (NullPointerException | WebDriverException e) {
            System.out.println(e.getMessage());
        } finally {
            if (webDriver != null) {
                webDriver.manage().timeouts().implicitlyWait(Long.parseLong(SetProperties.env.getValue("implicitlyWait")), TimeUnit.SECONDS);
                explicitWaitInSeconds = Integer.parseInt(SetProperties.env.getValue("explicitWait"));
                System.out.println("'"+browser+"' Browser is up and running");
            } else {
                System.out.println("'"+browser+"' Browser is fail to initialize");
            }
        }
        return webDriver;
    }
}
