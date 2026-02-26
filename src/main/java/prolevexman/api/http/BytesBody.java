package prolevexman.api.http;

public record BytesBody(
        byte[] value,
        String contentType) implements Body {}
