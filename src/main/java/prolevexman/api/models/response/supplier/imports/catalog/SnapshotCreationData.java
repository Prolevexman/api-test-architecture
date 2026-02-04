package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SnapshotCreationData(
        @JsonProperty("url") String url,
        @JsonProperty("fileType") String fileType,
        @JsonProperty("expiresAt") String expiresAt
) {}
