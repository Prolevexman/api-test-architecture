package prolevexman.api.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import prolevexman.api.di.ApiSteps;
import prolevexman.api.extensions.TestContextExtension;

@ExtendWith(TestContextExtension.class)
public class LoginDiTest {

    private final ApiSteps steps;

    public LoginDiTest(ApiSteps steps) {
        this.steps = steps;
    }

    @Test
    void loginWorksViaDi() {
        System.out.println(steps.auth().login(steps.defaultCredentials()));
    }
}

