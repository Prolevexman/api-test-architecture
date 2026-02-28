package prolevexman.api.auth;

import java.util.Objects;

public final class CaсhingTokenProvider implements TokenProvider {
    private final TokenProvider delegate;
    private volatile String cachedToken;

    public CaсhingTokenProvider(TokenProvider delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate");
    }

    @Override
    public String getToken() {
        String token = cachedToken;
        if (token != null) {
            return token;
        }
        synchronized (this) {
            if( cachedToken == null) cachedToken = requireNonBlank(delegate.getToken(), "delegate token");
            return cachedToken;
        }
    }

    private String requireNonBlank(String token, String delegateToken) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("TokenProvider returned blank " + delegateToken);
        }
        return token;
    }

    @Override
    public void invalidate() {
        cachedToken = null;
        delegate.invalidate();
    }
}
