package prolevexman.api.models.response.supplier.imports.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DownloadCatalogResponse(
        @JsonProperty("snapshotDate") String snapshotDate,
        @JsonProperty("createdDate") String createdDate,
        @JsonProperty("status") String status,
        @JsonProperty("url") String url
) {}
