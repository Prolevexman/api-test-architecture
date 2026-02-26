package prolevexman.api.clients;

import io.restassured.response.Response;

public interface ApiClient {

    Response get(String endpoint);

    Response post(Object payload, String endpoint);

    Response put(Object payload, String endpoint);

    Response delete(String payload);
}
