package prolevexman.api.auth;

import prolevexman.api.config.Config;

import java.util.Objects;

public class ConfigCredentialsProvider implements  CredentialsProvider{
    private final Config config;

    public ConfigCredentialsProvider(Config config) {
        this.config = Objects.requireNonNull(config, "config");
    }

    @Override
    public Credentials getCredentials() {
        String email = config.get("api.email");
        String password = config.get("api.password");
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("api.email and api.password are required");
        }
        return new Credentials(email, password);
    }
}
