package prolevexman.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import prolevexman.api.models.request.supplier.imports.catalog.PresignedUrlRequest;

public class UrlTest extends BaseTest{

    @Test
    void testUrl() {
        PresignedUrlRequest presignedUrlRequest = new PresignedUrlRequest("2026-02-26", "2026-02-26", "text/csv");
        Response response = supplierImportCatalogService.retrieveUrlForUpload(presignedUrlRequest);
        System.out.println(response.asPrettyString());
    }
}
