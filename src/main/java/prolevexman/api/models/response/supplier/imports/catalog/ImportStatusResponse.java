package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ImportStatusResponse(
        @JsonProperty("status") String status,
        @JsonProperty("snapshotDate") String snapshotDate,
        @JsonProperty("previousSnapshotDate") String previousSnapshotDate,
        @JsonProperty("missingSnapshotDates") List<String> missingSnapshotDates,
        @JsonProperty("message") String message
) {}
