package prolevexman.api.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class CompositeConfig implements Config {
    private final List<Config> configs;

    public CompositeConfig(Config... configs) {
        this.configs = List.copyOf(Arrays.asList(configs));
    }

    public CompositeConfig(List<Config> configs) {
        this.configs = List.copyOf(Objects.requireNonNull(configs, "configs"));
    }

    @Override
    public String get(String key) {
        for (Config c : configs) {
            if (c == null) continue;
            String v = c.get(key);
            if (v != null) return v;
        }
        return null;
    }

    @Override
    public String get(String key, String def) {
        String value = get(key);
        return value != null ? value : def;
    }
}

