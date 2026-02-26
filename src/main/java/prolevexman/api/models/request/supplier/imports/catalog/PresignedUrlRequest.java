package prolevexman.api.models.request.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PresignedUrlRequest(
    @JsonProperty("snapshotDate") String snapshotDate,
    @JsonProperty("previousSnapshotDate") String previousSnapshotDate,
    @JsonProperty("fileType") String fileType
){}