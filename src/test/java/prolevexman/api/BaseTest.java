package prolevexman.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import prolevexman.api.clients.ApiClient;
import prolevexman.api.clients.ApiClientFactory;
import prolevexman.api.services.SupplierImportCatalogService;
import prolevexman.api.session.SessionManager;
import prolevexman.listeners.MyTestListener;

@ExtendWith(MyTestListener.class)
public abstract class BaseTest {

    protected ApiClient apiClient;

    protected SupplierImportCatalogService supplierImportCatalogService;

    @BeforeEach
    void setUp() {
        apiClient = ApiClientFactory.authRAClient();

        supplierImportCatalogService = new SupplierImportCatalogService(apiClient);
    }

    @AfterEach
    void cleanup() {
        SessionManager.clear();
    }
}
