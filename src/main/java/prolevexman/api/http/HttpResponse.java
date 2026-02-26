package prolevexman.api.http;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public record HttpResponse(
        int statusCode,
        Map<String, List<String>> headers,
        byte[] bodyBytes,
        String contentType
) {
    public String bodyAsString(Charset charset) {
        if(bodyBytes == null) {
            return null;
        }
        return new String(bodyBytes, charset);
    }
}
