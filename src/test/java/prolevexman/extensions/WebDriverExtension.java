package prolevexman.extensions;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import prolevexman.core.driver.DriverFactory;
import prolevexman.annotations.Browser;

public class WebDriverExtension implements BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(prolevexman.extensions.WebDriverExtension.class);

    @Override
    public void beforeAll(ExtensionContext context) {
        boolean perClass = isPerClass(context);
        if (perClass) {
            WebDriver driver = DriverFactory.getDriver();
            getStore(context).put("driver", driver);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        boolean perClass = isPerClass(context);
        if (perClass) {
            quitDriver(context);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        boolean perClass = isPerClass(context);
        if (!perClass) {
            WebDriver driver = DriverFactory.getDriver();
            getStore(context).put("driver", driver);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        boolean perClass = isPerClass(context);
        if (perClass) {
            quitDriver(context);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType().equals(WebDriver.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return getDriver(extensionContext);
    }

    private boolean isPerClass(ExtensionContext context) {
        Browser annotation = context.getRequiredTestClass().getAnnotation(Browser.class);
        return annotation != null && annotation.perClass();
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(NAMESPACE);
    }

    private WebDriver getDriver(ExtensionContext context) {
        return getStore(context).get("driver", WebDriver.class);
    }

    private void quitDriver(ExtensionContext context) {
        WebDriver driver = getDriver(context);
        if (driver != null) {
            DriverFactory.quitDriver();
            getStore(context).remove("driver");
        }
    }
}
