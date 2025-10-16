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
}
