package prolevexman.api.http.interceptors;

import prolevexman.api.auth.TokenProvider;
import prolevexman.api.http.HttpRequest;
import prolevexman.api.http.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AuthInterceptor implements HttpInterceptor{
    private final TokenProvider tokenProvider;

    public AuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = Objects.requireNonNull(tokenProvider, "tokenProvider");
    }

    @Override
    public HttpResponse intercept(HttpRequest request, HttpExecutionChain chain) {
        String token = tokenProvider.getToken();

        HttpRequest withAuth = copyWithAuth(request, token);
        HttpResponse response = chain.proceed(withAuth);

        if(response.statusCode() == 401 || response.statusCode() == 403) {
            tokenProvider.invalidate();
        }
        return response;
    }

    private HttpRequest copyWithAuth(HttpRequest request, String token) {
        HttpRequest.Builder b = HttpRequest.builder()
                .method(request.method())
                .path(request.path())
                .body(request.body());

        Map<String, List<String>> query = request.query();
        if (query != null) {
            query.forEach((k, values) -> {
                if (values == null) return;
                for (String v: values) b.queryParam(k, v);
            });
        }

        Map<String, List<String>> headers = request.headers();
        if (headers != null) {
            headers.forEach((k, values) -> {
                if (values == null) return;
                for (String v: values) b.header(k, v);
            });
        }

        b.header("Authorization", "Bearer " + token);

        return b.build();
    }


}
