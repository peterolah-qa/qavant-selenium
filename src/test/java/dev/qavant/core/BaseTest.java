package dev.qavant.core;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * One fresh browser per test method = full isolation, no shared state.
 * BASE_URL can be overridden for preview environments:
 *   mvn test -Dqavant.baseUrl=https://deploy-preview-12--site.netlify.app
 */
public abstract class BaseTest {

    protected static final String BASE_URL =
            System.getProperty("qavant.baseUrl",
                    System.getenv().getOrDefault("QAVANT_BASE_URL", "https://qavant.dev"));

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {
        driver = DriverFactory.createChrome();
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
