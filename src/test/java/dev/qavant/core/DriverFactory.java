package dev.qavant.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Creates configured WebDriver instances.
 *
 * Local runs use Selenium Manager to resolve the matching chromedriver
 * automatically. When SELENIUM_REMOTE_URL is set (a Dockerised Selenium Grid /
 * standalone-chrome container, locally or in CI), the same ChromeOptions are
 * sent to a RemoteWebDriver instead — so local and containerised runs share
 * one code path and one browser configuration.
 *
 * Headless is the default (CI-first); run with -Dheadless=false to watch it.
 */
public final class DriverFactory {

    private DriverFactory() { }

    public static WebDriver createChrome() {
        ChromeOptions options = buildOptions();

        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        WebDriver driver = (remoteUrl == null || remoteUrl.isBlank())
                ? new ChromeDriver(options)
                : createRemote(remoteUrl, options);

        // implicit waits stay at ZERO — all waiting is explicit (see BasePage)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    private static ChromeOptions buildOptions() {
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
        return options;
    }

    private static WebDriver createRemote(String remoteUrl, ChromeOptions options) {
        try {
            return new RemoteWebDriver(new URL(remoteUrl), options);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid SELENIUM_REMOTE_URL: " + remoteUrl, e);
        }
    }
}