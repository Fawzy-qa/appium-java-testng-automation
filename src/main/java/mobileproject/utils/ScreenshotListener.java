package mobileproject.utils;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import mobileproject.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ScreenshotListener implements ITestListener {

    private static final String SCREENSHOT_DIR = System.getProperty("screenshot.dir", "target/screenshots");

    @Override
    public void onTestFailure(ITestResult result) {
        captureScreenshot(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        captureScreenshot(result, "SKIPPED");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Uncomment if you want screenshots of every passed test
        // captureScreenshot(result, "PASSED");
    }

    private void captureScreenshot(ITestResult result, String status) {
        AppiumDriver driver = extractDriver(result);
        if (driver == null) {
            System.err.println("[SCREENSHOT] No driver available for: " + result.getName());
            return;
        }

        // Don't try to screenshot if the session is already dead
        try {
            driver.getStatus();
        } catch (WebDriverException e) {
            System.err.println("[SCREENSHOT] Driver session is dead, skipping: " + e.getMessage());
            return;
        }

        byte[] screenshotBytes = null;
        try {
            screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            System.err.println("[SCREENSHOT] Failed to capture screenshot: " + e.getMessage());
            return;
        }

        String attachmentName = buildAttachmentName(result, status);

        // 1. Attach to Allure
        Allure.addAttachment(
                attachmentName,
                "image/png",
                new ByteArrayInputStream(screenshotBytes),
                ".png"
        );

        // 2. Save to disk for Jenkins archiving
        saveToDisk(screenshotBytes, attachmentName);

        System.out.println("[SCREENSHOT] " + status + " -> " + attachmentName);
    }

    private AppiumDriver extractDriver(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest)) {
            return null;
        }

        try {
            Field driverField = BaseTest.class.getDeclaredField("driver");
            driverField.setAccessible(true);
            return (AppiumDriver) driverField.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("[SCREENSHOT] Reflection failed to get driver: " + e.getMessage());
            return null;
        }
    }

    private String buildAttachmentName(ITestResult result, String status) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getName();

        // Include data provider parameters so "testSuccessfulRegistrationHappyPath"
        // becomes "testSuccessfulRegistrationHappyPath[Fawzy Fawzy, fawzyu6232h@gmail.com]"
        Object[] params = result.getParameters();
        String paramsSuffix = params.length > 0
                ? Arrays.deepToString(params)
                : "";

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        StringBuilder name = new StringBuilder();
        name.append(status).append("_");
        name.append(className).append(".").append(methodName);
        if (!paramsSuffix.isEmpty()) {
            // Sanitize for filesystem safety
            String safeParams = paramsSuffix.replaceAll("[\\\\/:*?\"<>|]", "_");
            name.append(safeParams);
        }
        name.append("_").append(timestamp);

        return name.toString();
    }

    private void saveToDisk(byte[] bytes, String fileName) {
        try {
            File dir = new File(SCREENSHOT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File destFile = new File(dir, fileName + ".png");
            FileUtils.writeByteArrayToFile(destFile, bytes);

        } catch (IOException e) {
            System.err.println("[SCREENSHOT] Failed to save to disk: " + e.getMessage());
        }
    }
}