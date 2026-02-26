package prolevexman.api.http;

public record NoBody() implements Body {
    public String contentType() {
        return null;
    }
}
