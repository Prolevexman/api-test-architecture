package prolevexman.api.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import prolevexman.api.models.request.supplier.imports.catalog.SnapshotCreationRequest;

import static io.restassured.RestAssured.given;

public class SupplierImportCatalogClient {

    public Response createSnapshot(String authToken, SnapshotCreationRequest snapshotCreationRequest) {
        if(authToken == null || authToken.isEmpty()) {
            throw new IllegalArgumentException("authToken can't be empty or null");
        }
        return given()
                .header("X-Auth-Token", authToken)
                .contentType(ContentType.JSON)
                .body(snapshotCreationRequest)
                .when()
                .post("/supplier/import/catalog/presigned-url");

    }
}
