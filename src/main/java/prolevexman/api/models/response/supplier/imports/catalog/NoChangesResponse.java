package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NoChangesResponse(
        @JsonProperty ("message") String message
) {}
