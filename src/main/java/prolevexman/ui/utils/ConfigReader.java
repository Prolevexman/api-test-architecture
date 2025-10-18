package prolevexman.ui.utils;

import prolevexman.core.driver.DriverFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigReader {
    private static final Logger LOG = Logger.getLogger(DriverFactory.class.getName());
    private static final Properties PROPERTIES = new Properties();

    static {
        loadBaseConfig();
        loadEnvConfig();
    }

    private static void loadBaseConfig() {
        try (InputStream base = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (base != null) {
                PROPERTIES.load(base);
            } else {
                System.out.println("Base config.properties not found, using defaults.");
            }
        } catch (IOException e) {
            System.err.println("Error reading base config.properties: " + e.getMessage());
        }
    }

    private static void loadEnvConfig() {
        String env = System.getProperty("env", PROPERTIES.getProperty("env", "dev"));
        String envFile = String.format("config/%s.properties", env);

        try (InputStream envStream = ConfigReader.class.getClassLoader().getResourceAsStream(envFile)) {
            if (envStream != null) {
                PROPERTIES.load(envStream);
            } else {
                System.out.println("Environment file not found for: " + env);
            }
        } catch (IOException e) {
            System.err.println("Error reading env config: " + e.getMessage());
        }
    }


    public static String get(String key) {
        return System.getProperty(key, PROPERTIES.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }
}
