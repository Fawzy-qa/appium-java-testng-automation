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


    @AndroidFindBy(accessibility = "Don’t have an account? Create one")
    private WebElement createAccountLink;

    @AndroidFindBy(accessibility = "Email or password is incorrect.")
    private WebElement invalidCredentialsError;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(1)")
    private WebElement passwordField;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"اختر اللغة - العربية\n" +
            "\uD83C\uDDF8\uD83C\uDDE6\n" +
            "العربية\")")
    private  WebElement languageArabic;

    // Using uiAutomator because there is no acc id or res id
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").text(\"البريد الإلكتروني\")")
    private  WebElement emailArabic;


    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").text(\"كلمة المرور\")")
    private  WebElement passwordArabic;

    @AndroidFindBy(accessibility = "Log in")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "Email or password is incorrect.")
    private  WebElement errorMessage;

    @AndroidFindBy(accessibility = "Password is required")
    private  WebElement passwordRequiredError;

    @AndroidFindBy(accessibility = "نسيت كلمة المرور؟")
    private  WebElement forgotPass;

    @AndroidFindBy(accessibility = "تسجيل الدخول")
    private  WebElement loginMsg;

    @AndroidFindBy(accessibility = "دخول كوحدة خدمة (باركود)")
    private  WebElement loginAsUnit;


    @AndroidFindBy(accessibility = "ليس لديك حساب؟ إنشاء حساب")
    private  WebElement accountRegister;

    @AndroidFindBy(accessibility = "التسجيل كمندوب (رفع مستمسكات)")
    private  WebElement deliveryRegister;

    @AndroidFindBy(accessibility = "اختر اللغة")
    private  WebElement langChoice;


    public LoginPage enterUsername(String email) {
        enterText(usernameField, email);
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

    public void selectArabicLanguage() {
        click(languageArabic);
    }

    public  boolean isLangChosenArabic() {
        return isDisplayed(langChoice);
    }
    public  boolean isDeliveryRegisterArabic() {
        return isDisplayed(deliveryRegister);
    }
    public  boolean isAccountRegisterArabic() {
        return isDisplayed(accountRegister);
    }
    public  boolean isLoginAsUnitArabic() {
        return isDisplayed(loginAsUnit);
    }
    public  boolean isLoginMsgArabic() {
        return isDisplayed(loginMsg);
    }
    public  boolean isForgotPassArabic() {
        return isDisplayed(forgotPass);
    }

    public boolean isPasswordArabic() {
        return isDisplayed(passwordArabic);
    }
    public boolean isEmailArabic() {
        return isDisplayed(emailArabic);
    }
    public boolean isLanguageArabic() {
        return isDisplayed(languageArabic);
    }

    public LoginPage tapCreateAccountLink() {
        AndroidActions.scrollUntilVisible(driver, createAccountLink, 3);
        click(createAccountLink);
        return this;
    }
    public boolean isPasswordRequiredErrorDisplayed() {
        return isDisplayed(passwordRequiredError);
    }

    public boolean isInvalidCredentialsErrorDisplayed() {
        return isDisplayed(invalidCredentialsError);
    }
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }
}