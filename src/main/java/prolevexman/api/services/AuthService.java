package prolevexman.api.services;

import prolevexman.api.http.*;
import prolevexman.api.models.auth.LoginRequestDto;
import prolevexman.api.models.auth.LoginResponseDto;
import prolevexman.api.support.ApiException;
import prolevexman.api.support.Json;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class AuthService {
    private final HttpClient http;
    public final Json json;

    public AuthService(HttpClient http, Json json) {
        this.http = Objects.requireNonNull(http, "http");
        this.json = Objects.requireNonNull(json, "json");
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Objects.requireNonNull(dto, "dto");

        String payload = json.toJson(dto);

        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.POST)
                .path("/api/v1/login")
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .body(new StringBody(payload, "application/json"))
                .build();

        HttpResponse response = http.execute(request);

        if(response.statusCode() != 200) {
            throw new ApiException("Login failed. status=" + response.statusCode(), request, response);
        }

//        String body = response.bodyAsString(StandardCharsets.UTF_8);
//        LoginResponseDto parsed = json.fromJson(body, LoginResponseDto.class);
//
//        if(parsed == null || parsed.token() == null || parsed.token().isBlank()) {
//            throw new ApiException("Login response has blank token", request, response);
//        }
//
//        return parsed;

        String token = response.bodyAsString(StandardCharsets.UTF_8);
        if (token == null || (token = token.trim()).isEmpty()) {
            throw new ApiException("Login returned empty token", request, response);
        }

        return new LoginResponseDto(token);
    }
}
