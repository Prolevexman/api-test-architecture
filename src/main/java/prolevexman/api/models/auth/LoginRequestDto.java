package prolevexman.api.models.auth;

public record LoginRequestDto(
        String email,
        String password
) {}
