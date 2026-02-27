package prolevexman.api.http.interceptors;

import prolevexman.api.http.HttpClient;
import prolevexman.api.http.HttpRequest;
import prolevexman.api.http.HttpResponse;

import java.util.List;
import java.util.Objects;

public final class InterceptorHttpClient implements HttpClient {
    private final HttpClient transport;
    private final List<HttpInterceptor> interceptors;

    public InterceptorHttpClient(HttpClient transport, List<HttpInterceptor> interceptors) {
        this.transport = Objects.requireNonNull(transport, "transport");
        this.interceptors = List.copyOf(Objects.requireNonNull(interceptors, "interceptors"));
    }

    @Override
    public HttpResponse execute(HttpRequest request) {
        HttpExecutionChain chain = new DefaultExecutionChain(transport, interceptors, 0);
        return chain.proceed(request);
    }

    private static final class DefaultExecutionChain implements HttpExecutionChain {
        private final HttpClient transport;
        private final List<HttpInterceptor> interceptors;
        private final int index;

        public DefaultExecutionChain(HttpClient transport, List<HttpInterceptor> interceptors, int index) {
            this.transport = transport;
            this.interceptors = interceptors;
            this.index = index;
        }

        @Override
        public HttpResponse proceed(HttpRequest request) {
            if (index >= interceptors.size()) {
                return transport.execute(request);
            }
            HttpInterceptor interceptor = interceptors.get(index);
            HttpExecutionChain next = new DefaultExecutionChain(transport, interceptors, index + 1);
            return interceptor.intercept(request, next);
        }
    }
}
