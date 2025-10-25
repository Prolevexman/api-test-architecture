package prolevexman.ui.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.*;
import prolevexman.core.driver.DriverFactory;

import java.util.List;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static prolevexman.ui.utils.LocatorResolver.byText;

public abstract class BasePage {

    protected WebDriverWait wait;
    protected WebDriver driver;
    protected static final int TIMEOUT_SECONDS = 10;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);


    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, ofSeconds(TIMEOUT_SECONDS));
    }

    protected void openPage(String url) {
        driver.get(url);
    }

    protected String getCurrentUrl() {
        return  driver.getCurrentUrl();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void goBack() {
        driver.navigate().back();
    }

    protected void clickElementWithCheckAndRetry(By locator) {
        logger.info("Attempting to click element: {}", locator);
        try {
            wait.pollingEvery(ofMillis(500))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(ElementClickInterceptedException.class)
                    .until(driver -> {
                        WebElement element = driver.findElement(locator);
                        if (element.isDisplayed() && element.isEnabled()) {
                            new Actions(driver)
                                    .moveToElement(element)
                                    .pause(ofMillis(500))
                                    .click()
                                    .perform();
                            logger.debug("Clicked element successfully: {}", locator);
                            return true;
                        } else {
                            logger.warn("Element not yet clickable: {}", locator);
                            return false;
                        }
                    });
        } catch (TimeoutException e) {
            logger.error("Failed to click element after {} seconds: {}", TIMEOUT_SECONDS, locator, e);
            throw new NoSuchElementException(
                    "Element not clickable after " + TIMEOUT_SECONDS + " seconds: " + locator, e);
        }
    }

    protected void clickElementWithCheck(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Element not available or not found" + locator, e);
        }
    }

    protected void typeText(By locator, String text) {
        try {
            WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(locator));
            inputField.clear();
            inputField.sendKeys(text);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Input field not found " + locator, e);
        }
    }

    protected void typeTextWithCheck(By locator, String text) {
        try {
            WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(locator));
            inputField.clear();
            inputField.sendKeys(text);

            String actualText = inputField.getAttribute("value");
            if (!actualText.equals(text)) {
                throw new AssertionError("Failed input text, expected: " + text + ", but was: " + actualText);
            }
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Input field not found " + locator, e);
        }
    }

    protected String getText(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch (Exception e) {
            throw new NoSuchElementException("Failed to get element text: " + locator);
        }
    }

    protected String getElementAttribute(By locator, String attribute) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String value = element.getAttribute(attribute);
        if (value == null) {
            throw new NoSuchElementException("Attribute: " + attribute + " not found for element: " + locator);
        }
        return value;
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return  false;
        }
    }

    protected boolean isElementEnabled(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void hoverOverElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            new Actions(driver)
                    .moveToElement(element)
                    .pause(ofMillis(500))
                    .perform();
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Failed to hover over element: " + locator, e);
        } catch (MoveTargetOutOfBoundsException e) {
            throw new RuntimeException("Element is outside the visible area: " + locator, e);
        }
    }

    protected void waitVisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitInvisibilityOfElement(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForTextToBePresent(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    protected List<WebElement> getElementsList(By locator) {
        List<WebElement> elements;
        try {
            elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            throw  new RuntimeException("Elements not found for locator: " + locator);
        }
        if (elements == null || elements.isEmpty()) {
            throw new NoSuchElementException("Element list is empty for locator: " + locator);
        }
        return elements;
    }

    protected void clickElementByText(By baseLocator, String text) {
        By locator = byText(baseLocator, text);
        clickElementWithCheckAndRetry(locator);
    }

    protected List<String> getTextFromElements(By locator) {
        List<WebElement> elements = getElementsList(locator);

        return elements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    private Object executeJs(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script, args);
    }

    protected void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        executeJs("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }
}
