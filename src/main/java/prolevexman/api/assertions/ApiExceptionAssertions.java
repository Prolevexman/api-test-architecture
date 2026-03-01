package prolevexman.api.assertions;

import prolevexman.api.support.ApiException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ApiExceptionAssertions {
    private ApiExceptionAssertions() {}

    public static ApiException assertStatus(ApiException ex, int expectedStatus) {
        Objects.requireNonNull(ex, "ApiException is null");
        Objects.requireNonNull(ex.response(), "ApiException.response is null");
        int actual = ex.response().statusCode();
        if (actual != expectedStatus) {
            throw new AssertionError(
                    "Expected HTTP " + expectedStatus + " but got " + actual + "\nBody:\n" + safeBody(ex)
            );
        }
        return ex;
    }

    public static ApiException assertBodyContains(ApiException ex, String substring) {
        Objects.requireNonNull(substring, "substring is null");
        String body = safeBody(ex);
        if (!body.contains(substring)) {
            throw new AssertionError("Expected response body to contain: " + substring + "\nBody:\n" + body);
        }
        return ex;
    }

    public static String safeBody(ApiException ex) {
        if (ex == null || ex.response() == null || ex.response().bodyBytes() == null) return "";
        return new String(ex.response().bodyBytes(), StandardCharsets.UTF_8);
    }
}
