package prolevexman.api.http;

public record StringBody(
        String value,
        String contentType) implements Body {}
