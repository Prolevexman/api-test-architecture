package prolevexman.api.http;

public sealed interface Body permits NoBody, StringBody, BytesBody {
    String contentType();
}
