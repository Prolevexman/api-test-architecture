package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SnapshotCreationResponse(
        @JsonProperty("status") String status,
        @JsonProperty("message") String message,
        @JsonProperty("data") SnapshotCreationData data,
        @JsonProperty("instructions") SnapshotCreationInstructions instructions
) {}