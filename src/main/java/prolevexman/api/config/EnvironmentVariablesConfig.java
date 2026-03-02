package prolevexman.api.config;

public final class EnvironmentVariablesConfig implements Config {

    @Override
    public String get(String key) {
        return System.getenv(toEnvKey(key));
    }

    @Override
    public String get(String key, String def) {
        String value = get(key);
        return value != null ? value : def;
    }

    private static String toEnvKey(String key) {
        if (key == null) return null;
        return key.replace(".", "_").toUpperCase();
    }
}

