package prolevexman.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import prolevexman.ui.base.BasePage;
import prolevexman.ui.utils.ConfigReader;

public class SettingsPage extends BasePage {

    private static final String URL = ConfigReader.get("settings.page.url");

    private final By pageHeader = By.xpath(".//h2[contains(text(), 'Settings')]");

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    public SettingsPage openSettingsPage() {
        openPage(URL);
        return this;
    }

    public boolean isLoaded() {
        return isElementDisplayed(pageHeader);
    }
}
