package prolevexman.api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import prolevexman.api.filters.AuthFilter;
import prolevexman.api.filters.LoggingFilter;
import prolevexman.api.services.AuthService;
import prolevexman.api.utils.TokenService;


public class ApiClientFactory {



    public static ApiClient authRAClient() {

        RequestSpecification spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new LoggingFilter())
                .addFilter(new AuthFilter(tokenService()))
                .build();
        return new RestAssuredClient(spec);
    }

    public static ApiClient unauthRAClient() {

        RequestSpecification spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new LoggingFilter())
                .build();
        return new RestAssuredClient(spec);
    }

    private static TokenService tokenService() {
        AuthService authService = new AuthService(unauthRAClient());

        return new TokenService(authService);
    }

}
