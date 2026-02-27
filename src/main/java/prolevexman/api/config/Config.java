package prolevexman.api.config;

public interface Config {

    String get(String key);
    String get(String key, String def);
}
