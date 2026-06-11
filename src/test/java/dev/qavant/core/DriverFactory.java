package dev.qavant.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Creates configured WebDriver instances.
 *
 * Selenium 4.6+ ships Selenium Manager, which resolves the matching
 * chromedriver automatically — no WebDriverManager dependency needed.
 * Headless is the default because the suite is CI-first; run with
 * -Dheadless=false to watch the browser locally.
 */
public final class DriverFactory {

    private DriverFactory() { }

    public static WebDriver createChrome() {
        ChromeOptions options = new ChromeOptions();
        if (!"false".equalsIgnoreCase(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--window-size=1440,900",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );
        WebDriver driver = new ChromeDriver(options);
        // implicit waits stay at ZERO — all waiting is explicit (see BasePage)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }
}
