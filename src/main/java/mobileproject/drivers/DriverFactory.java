package mobileproject.drivers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import mobileproject.config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    // The static method means we can call it without creating an object of the factory first
    public static AppiumDriver createDriver(String platformName) {
        AppiumDriver driver = null;
        Properties config = ConfigReader.getProperties();

        try {
            // Read the Appium server address from your config file
            URL serverUrl = URI.create(config.getProperty("appium.server.url")).toURL();

            // Dynamically build the capabilities based on the platform requested
            switch (platformName.toLowerCase()) {
                case "android":
                    UiAutomator2Options androidOptions = new UiAutomator2Options();
                    androidOptions.setDeviceName(config.getProperty("device.name")); // e.g., Poco X3
                    androidOptions.setAppPackage(config.getProperty("app.package"));
                    androidOptions.setAppActivity(config.getProperty("app.activity"));
                    androidOptions.setAutoGrantPermissions(true);

                    // Recommended for physical device testing: Ensures app state resets cleanly between test suites
                    androidOptions.setNoReset(false);

                    driver = new AndroidDriver(serverUrl, androidOptions);
                    break;

                case "ios":
                    XCUITestOptions iosOptions = new XCUITestOptions();
                    iosOptions.setDeviceName("iPhone 14 Pro");
                    iosOptions.setApp("/path/to/SauceLabs-Demo-App.ipa");

                    driver = new IOSDriver(serverUrl, iosOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported mobile platform: " + platformName);
            }
        } catch (MalformedURLException e) {
            System.err.println("The Appium Server URL provided is invalid.");
            e.printStackTrace();
        }

        return driver;
    }
}