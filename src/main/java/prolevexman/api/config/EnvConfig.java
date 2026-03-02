package prolevexman.api.config;

public final class EnvConfig implements Config {

    private final Config delegate = new CompositeConfig(
            new SystemPropertiesConfig(),
            new EnvironmentVariablesConfig(),
            new DotenvConfig()
    );

    @Override
    public String get(String key) {
        return delegate.get(key);
    }

    @Override
    public String get(String key, String def) {
        return delegate.get(key, def);
    }
}
