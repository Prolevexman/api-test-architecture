package prolevexman.api.services;

import io.restassured.response.Response;
import prolevexman.api.clients.ApiClient;
import prolevexman.api.models.request.login.LoginRequest;

public class AuthService {

    private static final String BASE_PATH = "/api/v1";
    private final ApiClient apiClient;

    public AuthService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response login(LoginRequest payload) {
        return apiClient.post(payload, BASE_PATH + "/login");
    }

    public String loginS(LoginRequest payload) {
        return apiClient.post(payload, BASE_PATH + "/login" ).toString();
    }
}
