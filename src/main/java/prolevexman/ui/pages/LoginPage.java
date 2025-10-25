package prolevexman.ui.pages;

import org.openqa.selenium.By;
import prolevexman.ui.base.BasePage;
import prolevexman.ui.utils.ConfigReader;

public class LoginPage extends BasePage {

    private static final String URL = ConfigReader.get("login.page.url");

    private final By usernameField = By.xpath(".//input[@id='sign-in_email']");
    private final By passwordField = By.xpath(".//input[@id='sign-in_password']");
    private final By loginButton = By.xpath(".//button[span[text()='Login']]");


}
