package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PresignedUrlResponse(
        @JsonProperty("status") String status,
        @JsonProperty("message") String message,
        @JsonProperty("data") PresignUrlData data,
        @JsonProperty("instructions") PresignUrlInstructions instructions
) {}