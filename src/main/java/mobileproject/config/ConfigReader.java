package mobileproject.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    public static Properties getProperties() {
        if (properties == null) {
            try {
                properties = new Properties();
                FileInputStream file = new FileInputStream("src//test//resources//config.properties");
                properties.load(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not read config.properties file");
            }
        }
        return properties;
    }
}