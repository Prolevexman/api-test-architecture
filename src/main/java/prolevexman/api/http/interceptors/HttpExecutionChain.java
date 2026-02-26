package prolevexman.api.http.interceptors;

import prolevexman.api.http.HttpResponse;

import java.net.http.HttpRequest;

public interface HttpExecutionChain {
    HttpResponse proceed(HttpRequest request);
}
