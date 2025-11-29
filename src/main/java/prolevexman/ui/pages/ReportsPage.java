package prolevexman.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import prolevexman.ui.base.BasePage;
import prolevexman.ui.utils.ConfigReader;

public class ReportsPage extends BasePage {

    private static final String URL = ConfigReader.get("reports.page.url");

    private final By pageHeader = By.xpath(".//h2[contains(text(), 'Reports')]");

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    public ReportsPage openReportsPage() {
        openPage(URL);
        waitVisibilityOfElement(pageHeader);
        return this;
    }

    public boolean isLoaded() {
        return isElementDisplayed(pageHeader);
    }
}
