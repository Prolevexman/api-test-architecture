package prolevexman.api.http.interceptors;

import prolevexman.api.http.HttpResponse;

import java.net.http.HttpRequest;

public interface HttpInterceptor {
    HttpResponse intercepr(HttpRequest request, HttpExecutionChain chain);
}
