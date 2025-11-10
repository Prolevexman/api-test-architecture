package prolevexman.ui.auth.positive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import prolevexman.annotations.Browser;
import prolevexman.extensions.WebDriverExtension;
import prolevexman.ui.pages.LoginPage;
import prolevexman.ui.pages.NavigationPanel;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(WebDriverExtension.class)
@Browser
public class SignInPositiveTest {

    private String username = "sup1dev@vdnew.test";
    private String pass = "Sdev-_-59]?";
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

        loginPage.fillUsername(username)
                .fillPassword(pass)
                .clickLoginButton();
        assertTrue(navigationPanel.isVisibleLogoutButton());

    }
}
