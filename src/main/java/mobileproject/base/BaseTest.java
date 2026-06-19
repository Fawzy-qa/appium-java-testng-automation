package mobileproject.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import mobileproject.drivers.DriverFactory;
import mobileproject.config.ConfigReader;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;

import mobileproject.utils.ScreenshotListener;
import org.testng.annotations.Listeners;

@Listeners(ScreenshotListener.class)
public class BaseTest {

    protected AppiumDriver driver;
    protected static Properties config;
    protected static AppiumDriverLocalService service;

    @BeforeSuite
    public void beforeSuite() {
        config = ConfigReader.getProperties();
        System.out.println("========== Test Suite Started ==========");
        System.out.println("Configurations loaded for Environment: " + config.getProperty("platform.name"));

        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\Fawzy\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();

        service.start();
        System.out.println("Appium Server Started");
    }

    @BeforeClass
    public void setUp() {
        if (config == null) {
            config = ConfigReader.getProperties();
        }

        String targetPlatform = config.getProperty("platform.name");
        System.out.println("Initializing " + targetPlatform + " Driver...");
        driver = DriverFactory.createDriver(targetPlatform);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // ⬇️⬇️⬇️ ADD THIS BLOCK ⬇️⬇️⬇️
    @BeforeMethod
    public void resetAppState() {
        String appPackage = config.getProperty("app.package");
        if (appPackage == null || appPackage.isEmpty()) {
            throw new RuntimeException("Add 'app.package=com.yourcompany.carzivo' to config.properties");
        }

        if (driver instanceof AndroidDriver) {
            AndroidDriver androidDriver = (AndroidDriver) driver;

            // 1. Kill the app if it's running
            try {
                androidDriver.terminateApp(appPackage);
                System.out.println("[@BeforeMethod] App terminated.");
            } catch (Exception e) {
                System.out.println("[@BeforeMethod] App was not running.");
            }

            // 2. Wipe all data (logs out the user, clears registration state, etc.)
            try {
                driver.executeScript("mobile: clearApp", Map.of("appId", appPackage));
                System.out.println("[@BeforeMethod] App data cleared.");
            } catch (Exception e) {
                System.out.println("[@BeforeMethod] clearApp failed: " + e.getMessage());
                // Fallback for older Appium versions
                try {
                    driver.executeScript("mobile: shell", Map.of("command", "pm clear " + appPackage));
                } catch (Exception e2) {
                    System.out.println("[@BeforeMethod] Shell clear also failed: " + e2.getMessage());
                }
            }

            // 3. Launch fresh (lands on login screen)
            androidDriver.activateApp(appPackage);
            System.out.println("[@BeforeMethod] App relaunched fresh for next test.");
        }
    }
    // ⬆️⬆️⬆️ END ADDED BLOCK ⬆️⬆️⬆️

    @AfterClass
    public void tearDown() {
        System.out.println("Closing Driver Session...");
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite() {
        if (service != null) {
            service.stop();
            System.out.println("Appium Server Stopped.");
        }
    }
}