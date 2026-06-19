package mobileproject.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class JsonReader {

    /**
     * Reads a named JSON array from the given file and converts it into a TestNG
     * Object[][] data provider, pulling exactly the fields listed in fieldNames,
     * IN THAT ORDER. The order/length of fieldNames must match the order/length
     * of the @Test method's parameters.
     *
     * @param jsonFilePath path to the JSON file
     * @param arrayName    name of the top-level JSON array to read (e.g. "happyPathRegisterData")
     * @param fieldNames   the keys to extract from each object, in parameter order
     */
    public static Object[][] getJsonData(String jsonFilePath, String arrayName, String... fieldNames) {
        try {
            FileReader reader = new FileReader(jsonFilePath);

            // 1. Read the ENTIRE JSON file as one big object
            JsonObject rootObject = new Gson().fromJson(reader, JsonObject.class);

            // 2. Extract ONLY the specific array we asked for (e.g., "happyPathRegisterData")
            JsonArray jsonArray = rootObject.getAsJsonArray(arrayName);

            // 3. Build a row per JSON object, with one column per requested field
            Object[][] data = new Object[jsonArray.size()][fieldNames.length];

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();

                for (int j = 0; j < fieldNames.length; j++) {
                    String key = fieldNames[j];
                    if (obj.has(key) && !obj.get(key).isJsonNull()) {
                        data[i][j] = obj.get(key).getAsString();
                    } else {
                        // Field not present in this JSON object (e.g. optional fields) -> empty string
                        data[i][j] = "";
                    }
                }
            }
            return data;

        } catch (Exception e) {
            System.err.println("Failed to read JSON test data from: " + jsonFilePath + " (array: " + arrayName + ")");
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}