package prolevexman.ui.auth.positive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import prolevexman.annotations.Browser;
import prolevexman.extensions.WebDriverExtension;
import prolevexman.testdata.TestUsers;
import prolevexman.testdata.User;
import prolevexman.ui.pages.LoginPage;
import prolevexman.ui.pages.NavigationPanel;
import prolevexman.ui.pages.ReportsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(WebDriverExtension.class)
@Browser
public class SignInPositiveTest {

    private final User supplier = TestUsers.supplier();
    private LoginPage loginPage;
    private NavigationPanel navigationPanel;

    @BeforeEach
    public void setUp(WebDriver driver) {
        loginPage = new LoginPage(driver);
        navigationPanel = new NavigationPanel(driver);
        loginPage.openLoginPage();
    }

    @DisplayName("SignIn supplier test")
    @Test
    void signInSupplierTest() {

        ReportsPage reportsPage = loginPage.fillUsername(supplier.getEmail())
                                        .fillPassword(supplier.getPass())
                                        .clickLoginButton();
        assertTrue(navigationPanel.isVisibleLogoutButton(), "Logout button not visible after login");
        assertTrue(reportsPage.isLoaded(), "Reports page is not loaded after login, current URL: " + loginPage.getDriver().getCurrentUrl());
    }
}
