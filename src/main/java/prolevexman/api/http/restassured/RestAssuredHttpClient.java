package prolevexman.api.http.restassured;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import prolevexman.api.config.ApiSettings;
import prolevexman.api.http.*;

import java.util.*;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class RestAssuredHttpClient implements HttpClient {

    private final ApiSettings settings;

    public RestAssuredHttpClient(ApiSettings settings) {
        this.settings = Objects.requireNonNull(settings, "settings");
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        Objects.requireNonNull(request, "request");

        RequestSpecification spec = RestAssured.given();

        spec.baseUri(settings.baseUri().toString());

        RestAssuredConfig config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", (int) settings.connectTimeout().toMillis())
                        .setParam("http.socket.timeout", (int) settings.readTimeout().toMillis()))
                .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

        spec.config(config);

        if (request.headers() != null) {
            request.headers().forEach((name, values) -> {
                if (values == null) return;
                for (String v: values) {
                    spec.header(name, v);
                }
            });
        }

        if (request.query() != null) {
            request.query().forEach((name, values) -> {
                if (values == null) return;
                for (String v: values) {
                    spec.queryParam(name, v);
                }
            });
        }

        Body body = request.body();
        if(body instanceof StringBody sb) {
            if(sb.contentType() != null) spec.contentType(sb.contentType());
            spec.body(sb.value());
        } else if (body instanceof BytesBody bb) {
            if(bb.contentType() != null) spec.contentType(bb.contentType());
            spec.body(bb.value());
        } else {

        }

        Method method = toRaMethod(request.method());
        Response raResponse = spec.request(method, request.path());

        int status = raResponse.statusCode();
        String contentType = raResponse.contentType();

        byte[] bytes = raResponse.asByteArray();

        Map<String, List<String>> headers = new HashMap<>();
        raResponse.headers().asList().forEach(h -> {
            headers.computeIfAbsent(h.getName(), k -> new ArrayList<>())
                    .add(h.getValue());
        });

        Map<String, List<String>> immutHeaders = new HashMap<>();
        headers.forEach((k, v) -> immutHeaders.put(k, List.copyOf(v)));

        return new HttpResponse(
                status,
                Map.copyOf(immutHeaders),
                bytes,
                contentType
        );
    }

    private Method toRaMethod(HttpMethod method) {
        return switch (method) {
            case GET -> Method.GET;
            case POST -> Method.POST;
            case PUT -> Method.PUT;
            case PATCH -> Method.PATCH;
            case DELETE -> Method.DELETE;
        };
    }
}
