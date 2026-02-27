package prolevexman.api.config;

public class EnvConfig implements Config{
    @Override
    public String get(String key) {
        String value = System.getProperty(key);
        if (value != null) {
            return value;
        }
        value = System.getenv(toEnvString(key));
        return value;
    }

    private String toEnvString(String key) {
        return key
                .replace(".", "_")
                .toUpperCase();
    }

    @Override
    public String get(String key, String def) {
        String value = get(key);
        return value != null ? value : def;
    }
}
