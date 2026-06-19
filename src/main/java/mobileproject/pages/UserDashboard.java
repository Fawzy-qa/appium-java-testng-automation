package mobileproject.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobileproject.base.BasePage;
import mobileproject.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class UserDashboard extends BasePage {

    public UserDashboard(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    // --- LOCATORS ---



    @AndroidFindBy(accessibility = "Express services")
    private WebElement userDash;

    @AndroidFindBy(accessibility = "Tow request")
    private WebElement requestTow;

    @AndroidFindBy(accessibility = "Request a spare part")
    private WebElement requestSparePart;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(0)")
    private WebElement partNameField;

    @AndroidFindBy(accessibility = "Offers available")
    private WebElement offersAvailableText;


    @AndroidFindBy(accessibility = "Submit the request")
    private WebElement submitRequestBtn;


    @AndroidFindBy(accessibility = "Request a workshop")
    private WebElement requestWorkshop;

    @AndroidFindBy(accessibility = "Request a transport truck")
    private WebElement requestTransport;

    @AndroidFindBy(accessibility = "The name of the piece should be clearer than that")
    private WebElement unclearPartNameError;

    @AndroidFindBy(accessibility = "❌ Failed to send request: Exception: Connection error: Exception: Failed to create workshop request: 409 — لا يمكن إنشاء أكثر من طلب ورشة بنفس الوقت. الرجاء إنهاء الطلب الحالي أولاً.")
    private WebElement duplicateWorkshopError;

    @AndroidFindBy(accessibility = "My requests")
    private WebElement myRequestsTab;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"SettingsTab 1 of 3\")")
    private WebElement settingsTab;


    // --- VERIFICATION METHODS ---


    public boolean isUserDashboardHeaderDisplayed() {
        return isDisplayed(userDash);
    }


    // --- ACTION METHODS ---

    public UserDashboard tapTowRequest() {
        click(requestTow);
        return this;
    }

    public UserDashboard tapRequestSparePart() {
        click(requestSparePart);
        return this;
    }

    public UserDashboard tapRequestWorkshop() {
        click(requestWorkshop);
        return this;
    }

    public UserDashboard tapRequestTransport() {
        click(requestTransport);
        return this;
    }

    // --- ACTION METHODS ---

    public UserDashboard enterPartName(String partName) {
        enterText(partNameField, partName);
        return this;
    }

    public void tapSubmitRequest() {
        AndroidActions.scrollDownThenUp(driver, submitRequestBtn);
        click(submitRequestBtn);
    }


    public UserDashboard hideKeyboard() {
        AndroidActions.hideKeyboard(driver);
        return this; // Returns the page so the chain can continue
    }

    // --- VERIFICATION METHODS ---

    public boolean isUnclearPartNameErrorDisplayed() {
        return isDisplayed(unclearPartNameError);
    }
    public boolean isOffersAvailableDisplayed() {
        return isDisplayed(offersAvailableText);

    }
    public boolean isDuplicateWorkshopErrorDisplayed() {
        return isDisplayed(duplicateWorkshopError);
    }

    public SettingsPage tapSettingsTab() {
        return new SettingsPage(driver);
    }

    public MyRequestsPage tapMyRequestsTab() {
        click(myRequestsTab);
        return new MyRequestsPage(driver);
    }

}