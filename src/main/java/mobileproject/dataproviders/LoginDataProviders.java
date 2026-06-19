package mobileproject.dataproviders;

import mobileproject.utils.JsonReader;
import org.testng.annotations.DataProvider;

public class LoginDataProviders {

    // Ensure the physical file is actually named "loginData.json" or "LoginData.json" and matches this exactly
    private static final String JSON_PATH = "src/test/resources/testdata/loginData.json";

    @DataProvider(name = "negativeLoginData")
    public static Object[][] getNegativeData() {
        return JsonReader.getJsonData(JSON_PATH, "negativeScenario",
                "scenario", "email", "password");
    }

    @DataProvider(name = "positiveLoginData")
    public static Object[][] getPositiveData() {
        return JsonReader.getJsonData(JSON_PATH, "positiveScenario",
                "scenario", "email", "password");
    }

    @DataProvider(name = "langChoice")
    public static Object[][] getLangData() {
        return JsonReader.getJsonData(JSON_PATH, "langChoice",
                "scenario");
    }

    @DataProvider(name = "invalidCredentialsData")
    public static Object[][] getInvalidCredentialsData() {
        // ⬅️ FIX: Added the keys and used JSON_PATH
        return JsonReader.getJsonData(JSON_PATH, "invalidCredentialsData",
                "scenario", "email", "password");
    }
}