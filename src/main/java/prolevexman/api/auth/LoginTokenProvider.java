package prolevexman.api.auth;

import prolevexman.api.models.auth.LoginRequestDto;
import prolevexman.api.models.auth.LoginResponseDto;
import prolevexman.api.services.AuthService;

import java.util.Objects;

public class LoginTokenProvider implements  TokenProvider{
    private final CredentialsProvider credentialsProvider;
    private final AuthService authService;

    public LoginTokenProvider(CredentialsProvider credentialsProvider, AuthService authService) {
        this.credentialsProvider = Objects.requireNonNull(credentialsProvider, "credentialsProvider");
        this.authService = Objects.requireNonNull(authService, "authService");
    }

    @Override
    public String getToken() {
        Credentials c = credentialsProvider.getCredentials();

        LoginResponseDto response = authService.login(new LoginRequestDto(c.username(), c.password()));

        return response.token();
    }

    @Override
    public void invalidate(){}
}
