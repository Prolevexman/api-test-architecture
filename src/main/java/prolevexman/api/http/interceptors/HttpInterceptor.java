package prolevexman.api.http.interceptors;

import prolevexman.api.http.HttpRequest;
import prolevexman.api.http.HttpResponse;

public interface HttpInterceptor {
    HttpResponse intercept(HttpRequest request, HttpExecutionChain chain);
}
