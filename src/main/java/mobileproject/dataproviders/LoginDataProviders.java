package mobileproject.dataproviders;

import mobileproject.utils.JsonReader;
import org.testng.annotations.DataProvider;

public class LoginDataProviders {

    // CRITICAL: In a separate class, DataProvider methods MUST be 'static'.
    // This allows TestNG to fetch the data without needing to instantiate this entire class.

    @DataProvider(name = "negativeLoginData")
    public static Object[][] getNegativeData() {
        return JsonReader.getJsonData("src/test/resources/testdata/loginData.json", "negativeScenarios");
    }

    @DataProvider(name = "positiveLoginData")
    public static Object[][] getPositiveData() {
        return JsonReader.getJsonData("src/test/resources/testdata/loginData.json", "positiveScenarios");
    }
}