package mobileproject.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobileproject.base.BasePage;
import mobileproject.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);    }



  // Using uiAutomator because there is no acc id or res id
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(0)")
    private WebElement usernameField;


    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(1)")
    private WebElement passwordField;


    @AndroidFindBy(accessibility = "Log in")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "Email or password is incorrect.")
    private WebElement errorMessage;

    @AndroidFindBy(accessibility = "Password is required")
    private WebElement passwordRequiredError;


    public LoginPage enterUsername(String username) {
        enterText(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }

    public LoginPage hideKeyboard() {
        AndroidActions.hideKeyboard(driver);
        return this; // Returns the page so the chain can continue
    }

    public void tapLoginButton() {
        click(loginButton);
    }

    public boolean isPasswordRequiredErrorDisplayed() {
        return isDisplayed(passwordRequiredError);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }
}