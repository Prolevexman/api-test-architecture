package prolevexman.api.http.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prolevexman.api.http.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public final class LoggingInterceptor implements HttpInterceptor {

    private static final Logger log = LogManager.getLogger(LoggingInterceptor.class);

    private static final Set<String> SENSITIVE_HEADERS = Set.of(
            "authorization",
            "cookie",
            "set-cookie"
    );

    private final int maxBodyChars;

    public LoggingInterceptor() {
        this(4000);
    }

    public LoggingInterceptor(int maxBodyChars) {
        this.maxBodyChars = Math.max(256, maxBodyChars);
    }

    @Override
    public HttpResponse intercept(HttpRequest request, HttpExecutionChain chain) {
        Instant start = Instant.now();
        logRequest(request);

        HttpResponse response = chain.proceed(request);

        Duration took = Duration.between(start, Instant.now());
        logResponse(request, response, took);
        return response;
    }

    private void logRequest(HttpRequest request) {
        if (!log.isInfoEnabled()) return;

        String headers = formatHeaders(request.headers());
        String query = formatQuery(request.query());
        String body = formatRequestBody(request.path(), request.body());

        log.info("""

==================== HTTP REQUEST ====================
{} {}
Query: {}
Headers:
{}
Body:
{}
======================================================
""", request.method(), request.path(), query, headers, body);
    }

    private void logResponse(HttpRequest request, HttpResponse response, Duration took) {
        if (!log.isInfoEnabled()) return;

        String headers = formatHeaders(response.headers());
        String body = formatResponseBody(request, response);

        log.info("""

==================== HTTP RESPONSE ====================
{} {} -> {} ({} ms)
Content-Type: {}
Headers:
{}
Body:
{}
=======================================================
""",
                request.method(),
                request.path(),
                response.statusCode(),
                took.toMillis(),
                response.contentType(),
                headers,
                body
        );
    }

    private String formatHeaders(Map<String, List<String>> headers) {
        if (headers == null || headers.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        headers.forEach((k, vals) -> {
            if (k == null) return;
            String keyLower = k.toLowerCase(Locale.ROOT);
            if (SENSITIVE_HEADERS.contains(keyLower)) {
                sb.append(k).append(": <masked>").append('\n');
                return;
            }
            if (vals == null || vals.isEmpty()) {
                sb.append(k).append(":").append('\n');
                return;
            }
            for (String v : vals) {
                sb.append(k).append(": ").append(v).append('\n');
            }
        });
        return sb.toString().trim();
    }

    private String formatQuery(Map<String, List<String>> query) {
        if (query == null || query.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        query.forEach((k, vals) -> {
            if (k == null || vals == null) return;
            for (String v : vals) {
                if (sb.length() > 0) sb.append("&");
                sb.append(k).append("=").append(v);
            }
        });
        return sb.toString();
    }

    private String formatRequestBody(String path, Body body) {
        if (body == null || body instanceof NoBody) return "";

        if (path != null && path.toLowerCase(Locale.ROOT).contains("login")) {
            return "<masked>";
        }

        if (body instanceof StringBody sb) {
            return abbreviate(maskSecrets(sb.value()));
        }
        if (body instanceof BytesBody bb) {
            return "<bytes:" + (bb.value() == null ? 0 : bb.value().length) + ">";
        }
        return "<unknown-body>";
    }

    private String formatResponseBody(HttpRequest request, HttpResponse response) {
        if (request.path().toLowerCase().contains("login")) return "<masked>";
        if (response == null || response.bodyBytes() == null) return "";
        String ct = response.contentType();
        if (ct != null && ct.toLowerCase(Locale.ROOT).contains("octet-stream")) {
            return "<bytes:" + response.bodyBytes().length + ">";
        }
        String s = response.bodyAsString(StandardCharsets.UTF_8);
        return abbreviate(maskSecrets(s));
    }

    private String abbreviate(String s) {
        if (s == null) return "";
        String t = s.trim();
        if (t.length() <= maxBodyChars) return t;
        return t.substring(0, maxBodyChars) + " ...(" + t.length() + " chars)";
    }

    private String maskSecrets(String s) {
        if (s == null) return null;
        String out = s;
        out = out.replaceAll("(?i)\"password\"\\s*:\\s*\"[^\"]*\"", "\"password\":\"<masked>\"");
        out = out.replaceAll("(?i)\"token\"\\s*:\\s*\"[^\"]*\"", "\"token\":\"<masked>\"");
        return out;
    }
}

