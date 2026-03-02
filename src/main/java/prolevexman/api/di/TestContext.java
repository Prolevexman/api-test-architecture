package prolevexman.api.di;

import prolevexman.api.auth.*;
import prolevexman.api.config.ApiSettings;
import prolevexman.api.config.ApiSettingsFactory;
import prolevexman.api.config.Config;
import prolevexman.api.config.EnvConfig;
import prolevexman.api.http.HttpClient;
import prolevexman.api.http.interceptors.AuthInterceptor;
import prolevexman.api.http.interceptors.HttpInterceptor;
import prolevexman.api.http.interceptors.InterceptorHttpClient;
import prolevexman.api.http.restassured.RestAssuredHttpClient;
import prolevexman.api.services.AuthService;
import prolevexman.api.support.Json;

import java.util.List;
import java.util.Objects;

public final class TestContext implements AutoCloseable {
    private final Config config;
    private final ApiSettings apiSettings;
    private final Json json;

    private final HttpClient transport;
    private final HttpClient publicClient;
    private final HttpClient authClient;

    private final CredentialsProvider credentialsProvider;
    private final TokenProvider tokenProvider;

    private final ApiServices services;
    private final ApiSteps steps;

    private TestContext(
            Config config,
            ApiSettings apiSettings,
            Json json,
            HttpClient transport,
            HttpClient publicClient,
            HttpClient authClient,
            CredentialsProvider credentialsProvider,
            TokenProvider tokenProvider,
            ApiServices services,
            ApiSteps steps
    ) {
        this.config = Objects.requireNonNull(config, "config");
        this.apiSettings = Objects.requireNonNull(apiSettings, "apiSettings");
        this.json = Objects.requireNonNull(json, "json");
        this.transport = Objects.requireNonNull(transport, "transport");
        this.publicClient = Objects.requireNonNull(publicClient, "publicClient");
        this.authClient = Objects.requireNonNull(authClient, "authClient");
        this.credentialsProvider = Objects.requireNonNull(credentialsProvider, "credentialsProvider");
        this.tokenProvider = Objects.requireNonNull(tokenProvider, "tokenProvider");
        this.services = Objects.requireNonNull(services, "services");
        this.steps = Objects.requireNonNull(steps, "steps");
    }

    public static TestContext createDefault() {
        Config cfg = new EnvConfig();
        ApiSettings settings = ApiSettingsFactory.from(cfg);
        Json json = Json.createDefault();

        HttpClient transport = new RestAssuredHttpClient(settings);

        List<HttpInterceptor> shared = List.of();

        HttpClient publicClient = new InterceptorHttpClient(transport, shared);
        AuthService authService = new AuthService(publicClient, json);

        CredentialsProvider creds = new ConfigCredentialsProvider(cfg);
        TokenProvider tokenProvider = new CachingTokenProvider(
                new LoginTokenProvider(creds, authService)
        );

        HttpClient authClient = new InterceptorHttpClient(
                transport,
                concat(shared, List.of(new AuthInterceptor(tokenProvider)))
        );

        ApiServices services = new ApiServices(publicClient, authClient, json, cfg, authService);
        ApiSteps steps = new ApiSteps(services, creds);

        return new TestContext(
                cfg,
                settings,
                json,
                transport,
                publicClient,
                authClient,
                creds,
                tokenProvider,
                services,
                steps
        );
    }

    private static List<HttpInterceptor> concat(List<HttpInterceptor> a, List<HttpInterceptor> b) {
        if (a.isEmpty()) return b;
        if (b.isEmpty()) return a;
        return java.util.stream.Stream.concat(a.stream(), b.stream()).toList();
    }

    public Config config() { return config; }
    public ApiSettings apiSettings() { return apiSettings; }
    public Json json() { return json; }

    public HttpClient transport() { return transport; }
    public HttpClient publicClient() { return publicClient; }
    public HttpClient authClient() { return authClient; }

    public CredentialsProvider credentialsProvider() { return credentialsProvider; }
    public TokenProvider tokenProvider() { return tokenProvider; }

    public ApiServices services() { return services; }
    public ApiSteps steps() { return steps; }

    @Override
    public void close() {
    }
}

