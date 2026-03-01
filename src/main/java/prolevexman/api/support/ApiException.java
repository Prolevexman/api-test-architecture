package prolevexman.api.support;

import prolevexman.api.http.HttpRequest;
import prolevexman.api.http.HttpResponse;

public class ApiException extends RuntimeException{

    private final HttpRequest request;
    private final HttpResponse response;

    public ApiException(String message, HttpRequest request, HttpResponse response) {
        super(message);
        this.request = request;
        this.response = response;
    }

    public HttpRequest request() {
        return request;
    }

    public HttpResponse response() {
        return response;
    }
}
