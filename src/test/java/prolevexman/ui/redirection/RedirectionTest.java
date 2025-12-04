package prolevexman.ui.redirection;

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
import prolevexman.ui.pages.SettingsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(WebDriverExtension.class)
@Browser
public class RedirectionTest {

    private final User supplier = TestUsers.supplier();
    SettingsPage settingsPage;
    LoginPage loginPage;

    @BeforeEach
    public void setUp(WebDriver driver) {
        settingsPage = new SettingsPage(driver);
        loginPage = new LoginPage(driver);
    }

    @DisplayName("Return-to-origin after login")
    @Test
    void returnToOriginLoginTest() {

        settingsPage.openSettingsPage();
        assertTrue(loginPage.isLoaded(), () -> "Not redirected to Login page");

        loginPage.login(supplier.getEmail(), supplier.getPass());
        assertTrue(settingsPage.isLoaded(), () -> "Not redirected to origin page after Login");

    }
}
