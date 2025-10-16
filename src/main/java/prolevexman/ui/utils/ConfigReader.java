package prolevexman.ui.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        String env = System.getProperty("env", "dev");
        String path = "src/main/resources/config" + env + ".properties";
        try (FileInputStream input = new FileInputStream(path)) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config for environment: " + env, e);
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing property: " + key);
        }
        return value;
    }
}
