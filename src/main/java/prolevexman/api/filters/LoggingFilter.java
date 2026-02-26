package prolevexman.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext context) {
        logRequest(requestSpec);
        Response response = context.next(requestSpec, responseSpec);
        logResponse(response);

        return response;
    }

    public void logRequest(FilterableRequestSpecification requestSpec) {
        logger.info("""
                        
                        ==================== REQUEST ====================
                        BASE URI: {}
                        
                        Headers:
                        {}
                        
                        Payload:
                        {}
                        ================================================
                        """,
                requestSpec.getBaseUri(),
                requestSpec.getHeaders(),
                (Object) requestSpec.getBody());
    }

    public void logResponse(Response response) {
        logger.info("""
                        
                        ==================== RESPONSE ====================
                        Status: {}
                        
                        Headers:
                        {}
                        
                        Body:
                        {}
                        ==================================================
                        """,
                response.statusCode(),
                response.headers(),
                (Object) response.getBody().asPrettyString());
    }
}
