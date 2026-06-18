package mobileproject.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import mobileproject.drivers.DriverFactory;
import mobileproject.config.ConfigReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    // Protected so that your test classes (which extend BaseTest) can access the driver
    protected AppiumDriver driver;
    protected Properties config;
    protected AppiumDriverLocalService service;

    @BeforeSuite
    public void beforeSuite() {
        // 1. Load the properties once before any tests run
        config = ConfigReader.getProperties();
        System.out.println("========== Test Suite Started ==========");
        System.out.println("Configurations loaded for Environment: " + config.getProperty("platform.name"));

        service = new AppiumServiceBuilder()
                // We will insert your specific path in the next step
                .withAppiumJS(new File("C:\\Users\\Fawzy\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();

        service.start();
        System.out.println("Appium Server Started");
    }


    @BeforeClass
    public void setUp() {
        // 2. Fetch the target platform from the config file (e.g., "Android" or "iOS")
        String targetPlatform = config.getProperty("platform.name");

        System.out.println("Initializing " + targetPlatform + " Driver...");

        // 3. Pass the platform to the factory so it knows which capabilities to build
        driver = DriverFactory.createDriver(targetPlatform);

        // 4. Set a baseline implicit wait (Explicit waits will be handled in BasePage)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Closing Driver Session...");
        // 5. Gracefully kill the session to free up the physical device port
        if (driver != null) {
            driver.quit();
        }
    }
    @AfterSuite
    public void afterSuite() {
        // 3. Always shut down the server when tests finish
        if (service != null) {
            service.stop();
            System.out.println("Appium Server Stopped.");
        }
    }

}