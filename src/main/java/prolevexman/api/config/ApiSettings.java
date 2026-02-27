package prolevexman.api.config;

import java.net.URI;
import java.time.Duration;

public record ApiSettings(
        URI baseUri,
        Duration connectTimeout,
        Duration readTimeout
) {}
