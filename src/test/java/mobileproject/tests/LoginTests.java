package mobileproject.tests;

import mobileproject.base.BaseTest;
import mobileproject.dataproviders.LoginDataProviders;
import mobileproject.pages.LoginPage;
import mobileproject.pages.UserDashboard;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LoginTests extends BaseTest {

    // Test 1: The Negative Path (Expected to stay on Login screen and show error)
    @Test(priority = 1,dataProvider = "negativeLoginData",dataProviderClass = LoginDataProviders.class, description = "Verify user cannot login with invalid credentials")
    public void testInvalidLoginFails(String scenario, String email, String password) {

// 1. Inject the scenario name directly into your console logs or Allure reports
        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("Testing credentials -> User: " + email);

        // 1. Initialize the Page Object
        LoginPage loginPage = new LoginPage(driver);

        // 2. Execute the action
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        // 3. Assert the outcome
        boolean isErrorShown = loginPage.isPasswordRequiredErrorDisplayed();
        Assert.assertTrue(isErrorShown, "Test Failed: The error message did not appear after a bad login attempt.");
    }

    @Test(priority = 2, dataProvider = "invalidCredentialsData", dataProviderClass = LoginDataProviders.class, description = "Verify error message when login credentials are structurally valid but incorrect")
    public void testInvalidCredentialsFails(String scenario, String email, String password) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("Testing credentials -> Email: " + email);

        LoginPage loginPage = new LoginPage(driver);

        // Execute the login flow
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        // Assert the specific error message appears
        boolean isErrorShown = loginPage.isInvalidCredentialsErrorDisplayed();

        Assert.assertTrue(isErrorShown,
                "Test Failed: The 'Email or password is incorrect.' error message did not appear.");
    }

    @Test(priority = 3,dataProvider = "langChoice",dataProviderClass = LoginDataProviders.class, description = "Verify that arabic language are displayed correctly")
    public void testArabicLang(String scenario) {

        // 1. Inject the scenario name directly into your console logs or Allure reports
        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");;

        LoginPage loginPage = new LoginPage(driver);

        // Replace these strings with a real username and password for your app
        loginPage.selectArabicLanguage();

        SoftAssert softAssert = new SoftAssert();

        // All of these must be called on the INSTANCE, not the class
        softAssert.assertTrue(loginPage.isLangChosenArabic(), "Language choice text is missing!");
        softAssert.assertTrue(loginPage.isDeliveryRegisterArabic(), "Delivery register text is missing!");
        softAssert.assertTrue(loginPage.isAccountRegisterArabic(), "Account register text is missing!");
        softAssert.assertTrue(loginPage.isLanguageArabic(), "Language text is missing!");
        softAssert.assertTrue(loginPage.isEmailArabic(), "Email text is missing!");
        softAssert.assertTrue(loginPage.isPasswordArabic(), "Password text is missing!");
        softAssert.assertTrue(loginPage.isLoginAsUnitArabic(), "Login as unit text is missing!");
        softAssert.assertTrue(loginPage.isLoginMsgArabic(), "Login message is missing!");
        softAssert.assertTrue(loginPage.isForgotPassArabic(), "Forgot password text is missing!");

        softAssert.assertAll();
    }
    // Test 2: The Positive Path (Expected to log in successfully)
    @Test(priority = 4,dataProvider = "positiveLoginData",dataProviderClass = LoginDataProviders.class, description = "Verify user can login successfully with valid credentials")
    public void testSuccessfulLogin(String scenario, String email, String password) {

        // 1. Inject the scenario name directly into your console logs or Allure reports
        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("Testing credentials -> User: " + email);

        LoginPage loginPage = new LoginPage(driver);

        // Replace these strings with a real username and password for your app
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        UserDashboard userPage = new UserDashboard(driver);
        SoftAssert softAssert = new SoftAssert();

        // Verify the main header loaded
        softAssert.assertTrue(userPage.isUserDashboardHeaderDisplayed(), "Dashboard header is missing!");

    }
}