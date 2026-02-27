package prolevexman.api.config;

import java.net.URI;
import java.time.Duration;

public class ApiSettingsFactory {

    private ApiSettingsFactory() {}

    public static ApiSettings from(Config config) {
        String baseUrl = config.get("api.baseUri");
         if(baseUrl == null) {
             throw new IllegalArgumentException("api.baseUri is required");
         }

         Long connectTimeoutMs = Long.parseLong(config.get("api.connectTimeoutMs", "3000"));
         Long readTimeoutMs = Long.parseLong(config.get("api.readTimeoutMs", "10000"));

         return new ApiSettings(
                 URI.create(baseUrl),
                 Duration.ofMillis(connectTimeoutMs),
                 Duration.ofMillis(readTimeoutMs)
         );
    }
}
