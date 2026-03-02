package prolevexman.api.di;

import prolevexman.api.config.Config;
import prolevexman.api.http.HttpClient;
import prolevexman.api.services.AuthService;
import prolevexman.api.support.Json;

import java.util.Objects;

public final class ApiServices {
    private final HttpClient publicClient;
    private final HttpClient authClient;
    private final Json json;
    private final Config config;

    private final AuthService authService;

    public ApiServices(HttpClient publicClient, HttpClient authClient, Json json, Config config, AuthService authService) {
        this.publicClient = Objects.requireNonNull(publicClient, "publicClient");
        this.authClient = Objects.requireNonNull(authClient, "authClient");
        this.json = Objects.requireNonNull(json, "json");
        this.config = Objects.requireNonNull(config, "config");
        this.authService = Objects.requireNonNull(authService, "authService");
    }

    public HttpClient publicClient() {
        return publicClient;
    }

    public HttpClient authClient() {
        return authClient;
    }

    public Json json() {
        return json;
    }

    public Config config() {
        return config;
    }

    public AuthService authService() {
        return authService;
    }
}

