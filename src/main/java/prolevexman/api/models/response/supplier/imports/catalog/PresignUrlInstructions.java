package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PresignUrlInstructions(
        @JsonProperty("description") String description,
        @JsonProperty("example") String example
) {}
