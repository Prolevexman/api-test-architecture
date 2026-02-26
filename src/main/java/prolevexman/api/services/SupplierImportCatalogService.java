package prolevexman.api.services;


import io.restassured.response.Response;
import prolevexman.api.clients.ApiClient;
import prolevexman.api.models.request.supplier.imports.catalog.NoChangesRequest;
import prolevexman.api.models.request.supplier.imports.catalog.PresignedUrlRequest;

public class SupplierImportCatalogService {

    private static final String BASE_PATH = "/supplier/import/catalog";

    private final ApiClient apiClient;

    public SupplierImportCatalogService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response retrieveUrlForUpload(PresignedUrlRequest payload) {
        return apiClient.post(payload, BASE_PATH + "/presigned-url" );
    }

//    public Response noChanges(NoChangesRequest payload, String token) {
//        setAuthToken(token);
//        return postRequest(payload, BASE_PATH + "/no-changes" );
//    }
//
//    public Response getImportStatus(String token) {
//        setAuthToken(token);
//        return getRequest(BASE_PATH + "/no-changes" );
//    }
//
//    public Response getDownloadedCatalog(String parameter, String token) {
//        setParameter(parameter);
//        setAuthToken(token);
//        return getRequest(BASE_PATH + "/no-changes" );
//    }
//
//    public Response getDownloadedCatalog(String token) {
//        setAuthToken(token);
//        return getRequest(BASE_PATH + "/no-changes" );
//    }



}
