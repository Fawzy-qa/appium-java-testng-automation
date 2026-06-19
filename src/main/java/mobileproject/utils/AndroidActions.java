package mobileproject.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

public class AndroidActions {

    /**
     * Performs a standard Swipe/Scroll across the screen.
     * Uses ratios (0.0 to 1.0) so it works perfectly on ANY screen size (phone or tablet).
     */
    public static void scroll(AppiumDriver driver, double startXRatio, double startYRatio, double endXRatio, double endYRatio) {
        // 1. Get the screen size of whatever device is currently running the test
        Dimension size = driver.manage().window().getSize();

        // 2. Calculate the exact pixel coordinates based on the ratios provided
        int startX = (int) (size.width * startXRatio);
        int startY = (int) (size.height * startYRatio);
        int endX = (int) (size.width * endXRatio);
        int endY = (int) (size.height * endYRatio);

        // 3. Create a virtual "finger"
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        // 4. Sequence the exact movements a human finger makes
        Sequence swipe = new Sequence(finger, 1);

        // Hover over the start point -> Press down -> Move to end point in 600ms -> Lift finger up
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // 5. Tell the driver to execute the sequence
        driver.perform(Collections.singletonList(swipe));


    }

    /**
     * Smart Scroll: Swipes down repeatedly until the target element is visible.
     * If not found below, returns to start and swipes up.
     */
    public static void scrollUntilVisible(AppiumDriver driver, WebElement element, int maxSwipes) {
        int swipeCount = 0;

        // 1. SEARCH DOWN (original behavior)
        while (swipeCount < maxSwipes) {
            try {
                if (element.isDisplayed()) {
                    System.out.println("Element found after " + swipeCount + " down swipes.");
                    return;
                }
            } catch (Exception e) {
                // Element not in DOM yet, keep scrolling
            }
            scrollDown(driver);
            swipeCount++;
        }

        // 2. RETURN TO STARTING POSITION so we can search upward
        System.out.println("Element not found below. Returning to top to search upward...");
        for (int i = 0; i < maxSwipes; i++) {
            scrollUp(driver);
        }

        // 3. SEARCH UP
        swipeCount = 0;
        while (swipeCount < maxSwipes) {
            try {
                if (element.isDisplayed()) {
                    System.out.println("Element found after " + swipeCount + " up swipes.");
                    return;
                }
            } catch (Exception e) {
                // Element not in DOM yet, keep scrolling
            }
            scrollUp(driver);
            swipeCount++;
        }

        throw new RuntimeException("Element not found after " + maxSwipes + " down and " + maxSwipes + " up swipes.");
    }




    /**
     * Performs a Long Press on a specific element.
     */
    public static void longPress(AppiumDriver driver, WebElement element, long durationInMillis) {
        // 1. Find the exact center of the element on the screen
        Point location = element.getLocation();
        Dimension size = element.getSize();
        int centerX = location.getX() + (size.getWidth() / 2);
        int centerY = location.getY() + (size.getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence longPress = new Sequence(finger, 1);

        // Move to the element -> Press down -> HOLD for the specified duration -> Lift up
        longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(new Pause(finger, Duration.ofMillis(durationInMillis)));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(longPress));
    }

    /**
     * Helper method for a standard "Scroll Down" (swiping from bottom to top)
     */
    /**
     * Standard "Scroll Down" (swiping from bottom to top to reveal lower content)
     */
    public static void scrollDown(AppiumDriver driver) {
        scroll(driver, 0.5, 0.8, 0.5, 0.2);
    }

    /**
     * Standard "Scroll Up" (swiping from top to bottom to reveal upper content)
     */
    public static void scrollUp(AppiumDriver driver) {
        scroll(driver, 0.5, 0.2, 0.5, 0.8);
    }
    public static void hideKeyboard(AppiumDriver driver) {
        try {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            if (androidDriver.isKeyboardShown()) {
                androidDriver.hideKeyboard();
                Thread.sleep(500); // Wait for keyboard slide-down animation
                System.out.println("Keyboard hidden.");
            }
        } catch (Exception e) {
            System.out.println("Keyboard not present or already hidden.");
        }
    }
}