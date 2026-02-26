package prolevexman.api.http.interceptors;

import prolevexman.api.http.HttpRequest;
import prolevexman.api.http.HttpResponse;

public interface HttpExecutionChain {
    HttpResponse proceed(HttpRequest request);
}
