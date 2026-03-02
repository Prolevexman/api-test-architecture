package prolevexman.api.config;

public final class SystemPropertiesConfig implements Config {

    @Override
    public String get(String key) {
        return System.getProperty(key);
    }

    @Override
    public String get(String key, String def) {
        String value = get(key);
        return value != null ? value : def;
    }
}

