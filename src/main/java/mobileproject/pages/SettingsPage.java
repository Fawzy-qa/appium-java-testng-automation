package mobileproject.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobileproject.base.BasePage;
import mobileproject.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class SettingsPage extends BasePage {

    public SettingsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    // --- LOCATORS ---

    // Assuming accessibility ID for the menu option
    @AndroidFindBy(accessibility = "Change password")
    private WebElement changePasswordMenu;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(0)")
    private WebElement currentPasswordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(1)")
    private WebElement newPasswordField;

    // Corrected to instance(2)
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(2)")
    private WebElement confirmNewPasswordField;

    @AndroidFindBy(accessibility = "changing")
    private WebElement changingButton;

    @AndroidFindBy(accessibility = "The new password is too short.")
    private WebElement passwordTooShortError;


    // --- ACTION METHODS ---

    public SettingsPage tapChangePasswordMenu() {
        click(changePasswordMenu);
        return this;
    }

    public SettingsPage enterCurrentPassword(String currentPass) {
        enterText(currentPasswordField, currentPass);
        return this;
    }

    public SettingsPage enterNewPassword(String newPass) {
        enterText(newPasswordField, newPass);
        return this;
    }

    public SettingsPage enterConfirmNewPassword(String confirmPass) {
        enterText(confirmNewPasswordField, confirmPass);
        return this;
    }

    public SettingsPage tapChangingButton() {
        AndroidActions.hideKeyboard(driver); // Always hide keyboard before submitting!
        click(changingButton);
        return this;
    }

    // --- VERIFICATION METHODS ---

    public boolean isPasswordTooShortErrorDisplayed() {
        return isDisplayed(passwordTooShortError);
    }
}