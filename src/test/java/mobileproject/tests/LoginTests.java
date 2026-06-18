package mobileproject.tests;

import mobileproject.base.BaseTest;
import mobileproject.dataproviders.LoginDataProviders;
import mobileproject.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    // Test 1: The Negative Path (Expected to stay on Login screen and show error)
    @Test(priority = 1,dataProvider = "negativeLoginData",dataProviderClass = LoginDataProviders.class, description = "Verify user cannot login with invalid credentials")
    public void testInvalidLoginFails(String scenario, String username, String password) {

// 1. Inject the scenario name directly into your console logs or Allure reports
        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("Testing credentials -> User: " + username);

        // 1. Initialize the Page Object
        LoginPage loginPage = new LoginPage(driver);

        // 2. Execute the action
        loginPage.enterUsername(username)
                .enterPassword(password)
                .tapLoginButton();

        // 3. Assert the outcome
        boolean isErrorShown = loginPage.isErrorMessageDisplayed();
        Assert.assertTrue(isErrorShown, "Test Failed: The error message did not appear after a bad login attempt.");
    }

    // Test 2: The Positive Path (Expected to log in successfully)
    @Test(priority = 2,dataProvider = "positiveLoginData",dataProviderClass = LoginDataProviders.class, description = "Verify user can login successfully with valid credentials")
    public void testSuccessfulLogin(String scenario, String username, String password) {

        // 1. Inject the scenario name directly into your console logs or Allure reports
        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("Testing credentials -> User: " + username);

        LoginPage loginPage = new LoginPage(driver);

        // Replace these strings with a real username and password for your app
        loginPage.enterUsername(username)
                .enterPassword(password)
                .tapLoginButton();

        /*
         * 4. Next Step Assertion:
         * Once the login is successful, the app will transition to a new screen (e.g., Home or Dashboard).
         * To assert this worked, you would eventually create a HomePage.java object and verify a UI element there.
         * * Example:
         * HomePage homePage = new HomePage(driver);
         * Assert.assertTrue(homePage.isProfileIconDisplayed(), "Test Failed: Did not navigate to Home Screen.");
         */
    }
}