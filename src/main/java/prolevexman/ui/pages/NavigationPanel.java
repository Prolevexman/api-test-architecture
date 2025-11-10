package prolevexman.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import prolevexman.ui.base.BasePage;
import prolevexman.ui.utils.ConfigReader;

public class NavigationPanel extends BasePage {

    private final By logoutButton = By.xpath(".//h6[text()='Log Out']");

    public NavigationPanel(WebDriver driver) {
        super(driver);
    }

    public boolean isVisibleLogoutButton() {
        return isElementDisplayed(logoutButton);
    }
}
