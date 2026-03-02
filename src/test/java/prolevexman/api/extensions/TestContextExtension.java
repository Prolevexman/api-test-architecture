package prolevexman.api.extensions;

import org.junit.jupiter.api.extension.*;
import prolevexman.api.di.ApiServices;
import prolevexman.api.di.ApiSteps;
import prolevexman.api.di.TestContext;
import prolevexman.api.steps.AuthSteps;

public final class TestContextExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final ExtensionContext.Namespace NS = ExtensionContext.Namespace.create(TestContextExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        store(context).getOrComputeIfAbsent(key(context), k -> TestContext.createDefault(), TestContext.class);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        TestContext tc = store(context).remove(key(context), TestContext.class);
        if (tc != null) tc.close();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Class<?> t = parameterContext.getParameter().getType();
        return t.equals(TestContext.class)
                || t.equals(ApiServices.class)
                || t.equals(ApiSteps.class)
                || t.equals(AuthSteps.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        TestContext tc = store(extensionContext)
                .getOrComputeIfAbsent(key(extensionContext), k -> TestContext.createDefault(), TestContext.class);
        Class<?> t = parameterContext.getParameter().getType();

        if (t.equals(TestContext.class)) return tc;
        if (t.equals(ApiServices.class)) return tc.services();
        if (t.equals(ApiSteps.class)) return tc.steps();
        if (t.equals(AuthSteps.class)) return tc.steps().auth();

        throw new ParameterResolutionException("Unsupported parameter type: " + t.getName());
    }

    private static ExtensionContext.Store store(ExtensionContext ctx) {
        return ctx.getRoot().getStore(NS);
    }

    private static String key(ExtensionContext ctx) {
        String className = ctx.getRequiredTestClass().getName();
        String methodId = ctx.getTestMethod()
                .map(m -> m.toGenericString())
                .orElse("<class>");
        return className + "#" + methodId;
    }
}

