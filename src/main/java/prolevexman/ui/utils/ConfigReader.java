package prolevexman.ui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigReader {
    private static final Logger LOG = Logger.getLogger(ConfigReader.class.getName());
    private static final Properties PROPERTIES = new Properties();

    private static volatile boolean initialazed = false;

    private ConfigReader() {}

    private static synchronized void init() {
        if (initialazed) return;
        loadBaseConfig();
        loadEnvConfig();
        initialazed = true;
    }

    private static void loadBaseConfig() {
        String basePath = "config/config.properties";
        try (InputStream base = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(basePath)) {
            if (base != null) {
                PROPERTIES.load(base);
                LOG.info("Loaded base config: " + basePath);
            } else {
                LOG.info("Base config not found at" + basePath + " - using defaults/system props.");
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to load base config: " + e.getMessage(), e);
        }
    }

    private static void loadEnvConfig() {
        String envFromSystem = System.getProperty("env");
        String envFromProps = PROPERTIES.getProperty("env");
        String env = envFromSystem != null ? envFromSystem : (envFromProps != null ? envFromProps : "dev");

        String envPath = String.format("config/%s.properties", env);
        try (InputStream envStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(envPath)) {
            if (envStream != null) {
                PROPERTIES.load(new InputStreamReader(envStream, StandardCharsets.UTF_8));
                LOG.info("Loaded env config: " + envPath);
            } else {
                System.out.println("Environment file not found for: " + envPath);
                LOG.warning("Env config not found for " + env + " at " + envPath);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Failed to load env config: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        if (initialazed) init();

        String value = System.getProperty(key);
        if (value != null) return value;

        value = System.getenv(toEnvKey(key));
        if (value != null) return value;

        return PROPERTIES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    public static String getEnvCredential(String role, String field, Integer index) {
        if (!initialazed) init();

        String env = System.getProperty("env", PROPERTIES.getProperty("env", "dev")).toUpperCase();

        String key;
        if (index == null) {
            key = String.format("%s_%s_%s", env, role.toUpperCase(), field.toUpperCase());
        } else {
            key = String.format("%s_%s_%s_%d", env, role.toUpperCase(), field.toUpperCase(), index);
        }

        return System.getenv(key);
    }

    public static String getEnvCredential(String role, String field) {
        return getEnvCredential(role, field, null);
    }

    private static String toEnvKey(String key) {
        if (key == null || key.trim().isEmpty()) return null;

        String cleanedKey = key.trim();
        cleanedKey = cleanedKey.replaceAll("[^A-Za-z0-9_]", "_");
        cleanedKey = cleanedKey.toUpperCase();
        cleanedKey = cleanedKey.replaceAll("_+", "_");

        return cleanedKey;
    }

    public static synchronized void reload() {
        PROPERTIES.clear();
        initialazed = false;
        init();
        LOG.info("Config reload");
    }
}
