package mobileproject.tests;

import mobileproject.base.BaseTest;
import mobileproject.dataproviders.UserDataProviders;
import mobileproject.pages.LoginPage;
import mobileproject.pages.MyRequestsPage;
import mobileproject.pages.SettingsPage;
import mobileproject.pages.UserDashboard;
import mobileproject.utils.AndroidActions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDashboardTests extends BaseTest {

    // --- POSITIVE SCENARIO ---
    @Test(priority = 1, dataProvider = "validSparePartData", dataProviderClass = UserDataProviders.class, description = "Verify user can successfully request a spare part")
    public void testRequestSparePart(String scenario, String email, String password, String partName) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        UserDashboard userDashboard = new UserDashboard(driver);
        userDashboard.tapRequestSparePart()
                .enterPartName(partName)
                .hideKeyboard()
                .tapSubmitRequest();

        Assert.assertTrue(userDashboard.isOffersAvailableDisplayed(),
                "Test Failed: The 'Offers available' page did not load.");
    }


    // --- NEGATIVE SCENARIO ---
    @Test(priority = 2, dataProvider = "invalidSparePartData", dataProviderClass = UserDataProviders.class, description = "Verify error message appears when part name is too short/unclear")
    public void testInvalidSparePartName(String scenario, String email, String password, String partName) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");

        // 1. Perform Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        // 2. Navigate Dashboard and Submit Invalid Request
        UserDashboard userDashboard = new UserDashboard(driver);
        userDashboard.tapRequestSparePart()
                .enterPartName(partName)
                .hideKeyboard()
                .tapSubmitRequest();

        // 3. Assert the Error Message
        boolean isErrorShown = userDashboard.isUnclearPartNameErrorDisplayed();

        Assert.assertTrue(isErrorShown,
                "Test Failed: The 'name should be clearer' error message did not appear.");
    }
// --- WORKSHOP REQUEST SCENARIOS ---

    @Test(priority = 3, dataProvider = "validWorkshopData", dataProviderClass = UserDataProviders.class, description = "Verify user can successfully submit a single workshop request")
    public void testSuccessfulWorkshopRequest(String scenario, String email, String password) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("User: " + email);

        // 1. Perform Login with Data Provider credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        // 2. Navigate and Submit Workshop Request
        UserDashboard userDashboard = new UserDashboard(driver);
        userDashboard.tapRequestWorkshop()
                .tapSubmitRequest();

        // 3. Assert the Outcome (Returns to Express Services)
        boolean isSuccessShown = userDashboard.isUserDashboardHeaderDisplayed();

        Assert.assertTrue(isSuccessShown,
                "Test Failed: User was not redirected back to the Dashboard (Express services) after submission.");
    }


    @Test(priority = 4, dataProvider = "duplicateWorkshopData", dataProviderClass = UserDataProviders.class, description = "Verify user cannot make more than 1 workshop request at a time")
    public void testMultipleWorkshopRequestsFail(String scenario, String email, String password) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");
        System.out.println("User: " + email);

        // 1. Perform Login with Data Provider credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        // 2. Submit FIRST Request
        UserDashboard userDashboard = new UserDashboard(driver);
        userDashboard.tapRequestWorkshop()
                .tapSubmitRequest();

        System.out.println("First request submitted successfully.");

        // Wait for the app to redirect back to the dashboard before clicking again
        userDashboard.isUserDashboardHeaderDisplayed();

        // 3. Submit SECOND Request immediately after
        userDashboard.tapRequestWorkshop()
                .tapSubmitRequest();

        System.out.println("Second request submitted, waiting for API rejection...");

        // 4. Assert the 409 Backend Error Message is shown
        boolean isErrorShown = userDashboard.isDuplicateWorkshopErrorDisplayed();

        Assert.assertTrue(isErrorShown,
                "Test Failed: The duplicate request error message did not appear.");
    }

    // --- MY REQUESTS SCENARIOS ---

    @Test(priority = 5, dataProvider = "cancelSparePartData", dataProviderClass = UserDataProviders.class, description = "Verify user can cancel a spare part request")
    public void testCancelSparePartRequest(String scenario, String email, String password) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");

        // 1. Perform Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(email)
                .enterPassword(password)
                .hideKeyboard()
                .tapLoginButton();

        // 2. Navigate to Dashboard -> My Requests
        UserDashboard userDashboard = new UserDashboard(driver);

        // This clicks 'My requests' and automatically hands off to the MyRequestsPage
        MyRequestsPage myRequestsPage = userDashboard.tapMyRequestsTab();

        // 3. Execute Cancellation Flow
        myRequestsPage.tapSparePartsTab()
                .tapCancellationButton()
                .tapYesCancelButton();

        // 4. Assert Outcome
        boolean isCancelled = myRequestsPage.isCancellationSuccessDisplayed();

        Assert.assertTrue(isCancelled,
                "Test Failed: The cancellation success message did not appear.");
    }
    // --- SETTINGS SCENARIOS ---

    @Test(priority = 6, dataProvider = "invalidChangePasswordData", dataProviderClass = UserDataProviders.class, description = "Verify error appears when new password is less than 6 characters")
    public void testChangePasswordTooShort(String scenario, String email, String currentPassword, String newPassword) {

        System.out.println("==========================================");
        System.out.println("Executing Scenario: [" + scenario + "]");

        // 1. Perform Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(email)
                .enterPassword(currentPassword)
                .hideKeyboard()
                .tapLoginButton();

        // 2. Navigate Dashboard -> Settings Tab
        UserDashboard userDashboard = new UserDashboard(driver);
        SettingsPage settingsPage = userDashboard.tapSettingsTab();

        // 3. Execute Password Change Flow
        settingsPage.tapChangePasswordMenu()
                .enterCurrentPassword(currentPassword)
                .enterNewPassword(newPassword)
                .enterConfirmNewPassword(newPassword) // Sending the same short password
                .tapChangingButton();

        // 4. Assert Outcome
        boolean isErrorShown = settingsPage.isPasswordTooShortErrorDisplayed();

        Assert.assertTrue(isErrorShown,
                "Test Failed: The 'password too short' error message did not appear.");
    }
}