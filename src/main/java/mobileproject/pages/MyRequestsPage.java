package mobileproject.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobileproject.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class MyRequestsPage extends BasePage {

    public MyRequestsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    // --- LOCATORS ---

    // Using the exact UiSelector you provided. Notice the escaped newline (\n) and quotes (\").
    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Spare parts Tab 1 of 4\")")
    private WebElement sparePartsTab;

    // Assuming accessibility ID. Update if it uses UiAutomator instead!
    @AndroidFindBy(accessibility = "cancellation")
    private WebElement cancellationButton;

    @AndroidFindBy(accessibility = "Yes, cancel")
    private WebElement yesCancelButton;

    // Updated locator with the exact success message
    @AndroidFindBy(accessibility = "The order has been successfully cancelled")
    private WebElement cancellationSuccessMessage;

    // --- ACTION METHODS ---

    public MyRequestsPage tapSparePartsTab() {
        click(sparePartsTab);
        return this;
    }

    public MyRequestsPage tapCancellationButton() {
        click(cancellationButton);
        return this;
    }

    public void tapYesCancelButton() {
        click(yesCancelButton);
    }

    // --- VERIFICATION METHODS ---

    public boolean isCancellationSuccessDisplayed() {
        return isDisplayed(cancellationSuccessMessage);
    }
}