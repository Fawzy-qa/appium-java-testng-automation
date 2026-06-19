package mobileproject.base;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class BasePage {

    protected AppiumDriver driver;
    protected static WebDriverWait wait;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected static void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Mobile-aware click:
     * 1. Waits for visibility
     * 2. Scrolls element to center of viewport if it's at the screen edge
     * 3. Pauses 300ms for any keyboard dismiss / layout animations
     * 4. Waits for clickability and clicks
     */
    protected void click(WebElement element) {
        waitForVisibility(element);
        scrollToCenterIfNeeded(element);

        // Allow keyboard dismiss animation / layout re-flow to settle
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void enterText(WebElement element, String text) {
        waitForVisibility(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    protected static boolean isDisplayed(WebElement element) {
        try {
            waitForVisibility(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * If the element's center is in the top 20% or bottom 20% of the screen,
     * perform a W3C swipe to bring it to the vertical center.
     * This prevents taps from being swallowed by the status bar or navigation bar.
     */
    private void scrollToCenterIfNeeded(WebElement element) {
        try {
            Dimension screen = driver.manage().window().getSize();
            Point loc = element.getLocation();
            Dimension size = element.getSize();

            int elementCenterY = loc.getY() + (size.getHeight() / 2);
            int safeTop = (int) (screen.height * 0.20);
            int safeBottom = (int) (screen.height * 0.80);

            if (elementCenterY < safeTop || elementCenterY > safeBottom) {
                int screenCenterY = screen.height / 2;
                int deltaY = screenCenterY - elementCenterY;

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);

                // Swipe from screen center by the calculated delta
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), screen.width / 2, screenCenterY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), screen.width / 2, screenCenterY - deltaY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
            }
        } catch (Exception e) {
            System.out.println("scrollToCenterIfNeeded skipped: " + e.getMessage());
        }
    }
}