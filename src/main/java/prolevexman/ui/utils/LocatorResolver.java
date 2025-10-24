package prolevexman.ui.utils;

import org.openqa.selenium.By;

public class LocatorResolver {
    /**
     * convert into By object
     * formats:
     *  css
     *  xpath
     * if no prefix -> CSS
     */
    public static By resolve(String locatorValue) {
        if (locatorValue == null || locatorValue.isEmpty()) {
            throw new IllegalArgumentException("Locator value is null or empty");
        }

        locatorValue = locatorValue.trim();

        if (locatorValue.startsWith("css=")) {
            return By.cssSelector(locatorValue.substring(4));
        } else if (locatorValue.startsWith("xpath=")) {
            return By.xpath(locatorValue.substring(6));
        } else {
            return By.cssSelector(locatorValue);
        }
    }

    public static By fromProperty(String key) {
        String value = ConfigReader.get(key);
        return resolve(value);
    }

    public static By fromPropertyOrDefault(String key, String defaultValue) {
        String value;
        try {
            value = ConfigReader.get(key);
        } catch (RuntimeException e) {
            value = defaultValue;
        }
        return resolve(value);
    }

    public static By byText(By baseLocator, String text) {
        String baseXpath = "";

        String locatorString = baseLocator.toString();

        if (locatorString.startsWith("By.xpath: ")) {
            baseXpath = locatorString.replace("By.xpath: ", "");
        } else if (locatorString.startsWith("By.cssSelector: ")) {
            String css = locatorString.replace("By.cssSelector: ", "");
            baseXpath = "//*[" + "contains(concat(' ', normalize-space(@class), ' '), ' "
                    + css.replace(".", "") + " ')]";
        } else {
            throw new IllegalArgumentException("Unsupported locator type for byText: " + baseLocator);
        }

        String xpathWithText = baseXpath + String.format("//*[normalize-space(text())='%s']", text);
        return By.xpath(xpathWithText);
    }
}
