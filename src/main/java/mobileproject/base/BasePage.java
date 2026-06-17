package mobileproject.base;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected AppiumDriver driver;
    protected WebDriverWait wait;

    // Constructor: This forces every child page to hand over the driver and initializes the Wait
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        // Sets a standard explicit wait of 10 seconds for all pages across the framework
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Core Action Wrappers
     * These methods intercept standard Selenium actions to ensure the element is
     * fully ready before interacting, preventing "Element Not Interactable" exceptions.
     */

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void click(WebElement element) {
        // Wait for it to exist visually, then wait for it to be clickable, then click
        waitForVisibility(element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void enterText(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            waitForVisibility(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false; // If the wait times out, the element isn't displayed
        }
    }
}