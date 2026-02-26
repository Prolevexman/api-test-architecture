package prolevexman.api.models.request.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NoChangesRequest(
        @JsonProperty("snapshotDate") String snapshotDate
) {}
