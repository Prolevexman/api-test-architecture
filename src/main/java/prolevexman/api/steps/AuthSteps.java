package prolevexman.api.steps;

import io.qameta.allure.Step;
import prolevexman.api.assertions.AuthAssertions;
import prolevexman.api.auth.Credentials;
import prolevexman.api.models.auth.LoginRequestDto;
import prolevexman.api.models.auth.LoginResponseDto;
import prolevexman.api.services.AuthService;

import java.util.Objects;

public class AuthSteps {
    private final AuthService authService;

    public AuthSteps(AuthService authService) {
        this.authService = Objects.requireNonNull(authService, "authService");
    }

    @Step("Login as user {creds.username}")
    public LoginResponseDto login(Credentials creds) {
        Objects.requireNonNull(creds, "creds");

        LoginResponseDto dto = authService.login(
                new LoginRequestDto(creds.username(), creds.password())
        );

        return AuthAssertions.assertValidLogin(dto);
    }

    @Step("Get auth token for {creds.username}")
    public String token(Credentials creds) {
        return login(creds).token();
    }
}
