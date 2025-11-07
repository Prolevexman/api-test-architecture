package prolevexman.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import prolevexman.ui.base.BasePage;
import prolevexman.ui.utils.ConfigReader;

public class LoginPage extends BasePage {

    private static final String URL = ConfigReader.get("login.page.url");

    private final By usernameField = By.xpath(".//input[@id='sign-in_email']");
    private final By passwordField = By.xpath(".//input[@id='sign-in_password']");
    private final By loginButton = By.xpath(".//button[span[text()='Login']]");
    private final By registerNow = By.xpath(".//a[@href='/auth/sign-up']");
    private final By forgotPassword = By.xpath(".//a[contains(text(), 'Forgot password')]");
    private final By authError = By.xpath(".//span[contains(., 'Auth credential is malformed')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage openLoginPage() {
        openPage(URL);
        waitVisibilityOfElement(loginButton);
        return this;
    }

    public LoginPage fillUsername(String username) {
        typeText(usernameField, username);
        return this;
    }

    public LoginPage fillPassword(String password) {
        typeText(passwordField, password);
        return this;
    }

    public LoginPage clickLoginButton() {
        clickElementWithCheck(loginButton);
        return this;
    }

    public LoginPage login(String username, String password) {
        fillUsername(username);
        fillPassword(password);
        clickLoginButton();
        return this;
    }

    public String getAuthErrorMessage() {
        return getText(authError);
    }

    public RegisterPage clickRegisterNow() {
        clickElementWithCheck(registerNow);
        return new RegisterPage();
    }

    public LoginPage clickForgotPassword() {
        clickElementWithCheck(forgotPassword);
        return this;
    }

}
