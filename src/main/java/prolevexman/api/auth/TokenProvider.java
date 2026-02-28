package prolevexman.api.auth;

public interface TokenProvider {
    String getToken();
    default void invalidate() {}
}
