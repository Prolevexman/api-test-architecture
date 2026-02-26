package prolevexman.api.clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredClient implements ApiClient{

    private final RequestSpecification spec;
    private RequestSpecification api;

    public RestAssuredClient(RequestSpecification spec) {
        this.spec = spec;
        api = given().spec(spec);
    }

    @Override
    public Response get(String endpoint) {
        return api.get(endpoint);
    }

    @Override
    public Response post(Object payload, String endpoint) {
        return api.body(payload).post(endpoint);
    }

    @Override
    public Response put(Object payload, String endpoint) {
        return api.body(payload).put(endpoint);
    }

    @Override
    public Response delete(String endpoint) {
        return api.delete(endpoint);
    }
}
