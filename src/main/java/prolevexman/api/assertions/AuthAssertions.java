package prolevexman.api.assertions;

import prolevexman.api.models.auth.LoginResponseDto;

import java.util.Objects;

public final class AuthAssertions {
    private AuthAssertions() {}

    public static LoginResponseDto assertValidLogin(LoginResponseDto dto) {
        Objects.requireNonNull(dto, "LoginResponseDto is null");
        Objects.requireNonNull(dto.token(), "Token is null");
        if (dto.token().isBlank()) {
            throw new AssertionError("Token is blank");
        }

        int parts = dto.token().split("\\.").length;
        if (parts != 3) {
            throw new AssertionError("Token doesn't look like a JWT (expected 3 dot-separated parts)");
        }

        return dto;
    }
}
