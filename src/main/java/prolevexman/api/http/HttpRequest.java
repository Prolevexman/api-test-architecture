package prolevexman.api.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record HttpRequest (
        HttpMethod method,
        String path,
        Map<String, List<String>> headers,
        Map<String, List<String>> query,
        Body body
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private HttpMethod method;
        private String path;
        private Map<String, List<String>> headers = new HashMap<>();
        private Map<String, List<String>> query = new HashMap<>();
        private Body body = new NoBody();

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder header(String name, String value) {
            headers.computeIfAbsent(name, k -> new ArrayList<>())
                    .add(value);
            return this;
        }

        public Builder queryParam(String name, String value) {
            query.computeIfAbsent(name, k -> new ArrayList<>())
                    .add(value);
            return this;
        }

        public Builder body(Body body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            Map<String, List<String>> immutableHeaders = new HashMap<>();
            headers.forEach((k,v) -> immutableHeaders.put(k, List.copyOf(v)));

            Map<String, List<String>> immutableQuery = new HashMap<>();
            query.forEach((k,v) -> immutableQuery.put(k, List.copyOf(v)));
            return new HttpRequest(
                    method,
                    path,
                    Map.copyOf(immutableHeaders),
                    Map.copyOf(immutableQuery),
                    body
            );
        }
    }
}
