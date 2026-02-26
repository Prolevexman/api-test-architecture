package prolevexman.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import prolevexman.api.utils.TokenService;

public class AuthFilter implements Filter {

    private final TokenService tokenService;

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext context) {
        requestSpec.header(
                "X-Auth-Token", tokenService.getToken()
        );
        return context.next(requestSpec, responseSpec);
    }
}
