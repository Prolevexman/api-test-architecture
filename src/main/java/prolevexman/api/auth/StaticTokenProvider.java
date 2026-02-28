package prolevexman.api.auth;

import java.util.Objects;

public class StaticTokenProvider implements TokenProvider{
    private final String token;

    public StaticTokenProvider(String token) {
        this.token = Objects.requireNonNull(token, "token");
    }

    @Override
    public String getToken() {
        if (token.isBlank()) {
            throw new IllegalArgumentException("Static token is blank");
        }
        return token;
    }
}
