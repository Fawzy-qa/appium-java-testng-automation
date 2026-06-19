package mobileproject.dataproviders;

import mobileproject.utils.JsonReader;
import org.testng.annotations.DataProvider;

public class UserDataProviders {

    private static final String JSON_PATH = "src/test/resources/testdata/userData.json";

    // Note: If you aren't using this combined array anymore, you can delete this method!
    @DataProvider(name = "sparePartData")
    public static Object[][] getSparePartData() {
        return JsonReader.getJsonData(JSON_PATH, "sparePartData",
                "scenario", "email", "password", "partName");
    }

    @DataProvider(name = "validSparePartData")
    public static Object[][] getValidSparePartData() {
        return JsonReader.getJsonData(JSON_PATH, "validSparePartData",
                "scenario", "email", "password", "partName");
    }

    @DataProvider(name = "invalidSparePartData")
    public static Object[][] getInvalidSparePartData() {
        return JsonReader.getJsonData(JSON_PATH, "invalidSparePartData",
                "scenario", "email", "password", "partName");
    }

    @DataProvider(name = "validWorkshopData")
    public static Object[][] getValidWorkshopData() {
        return JsonReader.getJsonData(JSON_PATH, "validWorkshopData",
                "scenario", "email", "password");
    }

    @DataProvider(name = "duplicateWorkshopData")
    public static Object[][] getDuplicateWorkshopData() {
        return JsonReader.getJsonData(JSON_PATH, "duplicateWorkshopData",
                "scenario", "email", "password");
    }

    @DataProvider(name = "cancelSparePartData")
    public static Object[][] getCancelSparePartData() {
        return JsonReader.getJsonData(JSON_PATH, "cancelSparePartData",
                "scenario", "email", "password");
    }

    @DataProvider(name = "invalidChangePasswordData")
    public static Object[][] getInvalidChangePasswordData() {
        // Notice this one has 4 arguments matching the JSON you created for it
        return JsonReader.getJsonData(JSON_PATH, "invalidChangePasswordData",
                "scenario", "email", "password", "newPassword");
    }
}