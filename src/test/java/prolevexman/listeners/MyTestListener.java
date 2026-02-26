package prolevexman.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.*;

import java.util.Optional;

public class MyTestListener implements TestWatcher,
                                        BeforeEachCallback,
                                        AfterEachCallback,
                                        TestExecutionExceptionHandler {

    private static final Logger logger = LogManager.getLogger(MyTestListener.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        logger.info("Start test: {}",
                context.getDisplayName());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        logger.info("End test: {}",
                context.getDisplayName());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.info("Test is disabled: {} {}",
                context.getRequiredTestMethod().getName(),
                reason.orElse(""));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("Passed: {}",
                context.getRequiredTestMethod().getName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("Aborted: {}",
                context.getRequiredTestMethod().getName());
        //logger.debug("Abort reason: ", cause);

    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.error("Failed: {}",
                context.getRequiredTestMethod().getName());
        //logger.debug("Fail reason: ", cause);
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable cause) throws Throwable {
        logger.error("Exception in test lifecycle: {} {}",
                context.getDisplayName(),
                cause);
        throw cause;
    }

}
