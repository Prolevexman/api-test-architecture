package prolevexman.api.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public final class DotenvConfig implements Config {
    private final Dotenv dotenv;

    public DotenvConfig() {
        this(Dotenv.configure()
                .filename(".env")
                .ignoreIfMissing()
                .ignoreIfMalformed()
                .load());
    }

    public DotenvConfig(Dotenv dotenv) {
        this.dotenv = Objects.requireNonNull(dotenv, "dotenv");
    }

    @Override
    public String get(String key) {
        return dotenv.get(toEnvKey(key));
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

