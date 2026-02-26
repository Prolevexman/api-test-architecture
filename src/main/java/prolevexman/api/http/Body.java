package prolevexman.api.http;

sealed interface Body permits NoBody, StringBody, BytesBody {
    String contentType();
}
