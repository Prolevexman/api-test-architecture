package prolevexman.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import prolevexman.ui.utils.ConfigReader;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class DriverFactory {
    private static final Logger LOG = Logger.getLogger(DriverFactory.class.getName());

    private static final ThreadLocal<WebDriver> THL_DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        WebDriver driver = THL_DRIVER.get();
        if (Objects.isNull(driver)) {
            driver = createDriver();
            THL_DRIVER.set(driver);
        }
        return driver;
    }

    private static WebDriver createDriver() {
        String browser = ConfigReader.get("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));
        String remoteUrl = ConfigReader.get("remote.url", "");

        LOG.info(() -> "Creating WebDriver. browser=" + browser + ", headless=" + headless + ", remote=" + remoteUrl);

        WebDriver driver;

        if (!remoteUrl.isEmpty()) {
            throw new UnsupportedOperationException("Remote WebDriver not implemented in this snippet");
        }

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");
                driver =new ChromeDriver(options);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = THL_DRIVER.get();
        if(driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                LOG.warning("Error while quitting WebDriver: " + e.getMessage());
            } finally {
                THL_DRIVER.remove();
            }
        }
    }
}
