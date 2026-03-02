package prolevexman.api.di;

import prolevexman.api.auth.Credentials;
import prolevexman.api.auth.CredentialsProvider;
import prolevexman.api.steps.AuthSteps;

import java.util.Objects;

public final class ApiSteps {
    private final ApiServices services;
    private final CredentialsProvider credentialsProvider;

    private AuthSteps authSteps;

    public ApiSteps(ApiServices services, CredentialsProvider credentialsProvider) {
        this.services = Objects.requireNonNull(services, "services");
        this.credentialsProvider = Objects.requireNonNull(credentialsProvider, "credentialsProvider");
    }

    public AuthSteps auth() {
        if (authSteps == null) {
            authSteps = new AuthSteps(services.authService());
        }
        return authSteps;
    }

    public Credentials defaultCredentials() {
        return credentialsProvider.getCredentials();
    }
}

