package mobileproject.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class JsonReader {

    // Notice the two new parameters: the file path, and the name of the array we want
    public static Object[][] getJsonData(String jsonFilePath, String arrayName) {
        try {
            FileReader reader = new FileReader(jsonFilePath);

            // 1. Read the ENTIRE JSON file as one big object
            JsonObject rootObject = new Gson().fromJson(reader, JsonObject.class);

            // 2. Extract ONLY the specific array we asked for (e.g., "negativeScenarios")
            JsonArray jsonArray = rootObject.getAsJsonArray(arrayName);

            // 3. Create our 2D array dynamically based on the size of that specific JSON array
            Object[][] data = new Object[jsonArray.size()][3];

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();
                // Extracting the keys. (If you have tests with 3 parameters later, you'd add a data[i][2] here)
                data[i][0] = obj.get("scenario").getAsString();
                data[i][1] = obj.get("email").getAsString();
                data[i][2] = obj.get("password").getAsString();

            }
            return data;

        } catch (Exception e) {
            System.err.println("Failed to read JSON test data from: " + jsonFilePath);
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}