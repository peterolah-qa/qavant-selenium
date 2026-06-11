package dev.qavant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page object for the qavant.dev landing page.
 * Tests never look up elements directly — locators live here, once.
 */
public class HomePage extends BasePage {

    // --- locators ---
    private static final By HERO_TITLE   = By.id("heroTitle");
    private static final By NAV_LINKS    = By.cssSelector(".nav a[href^='#']");
    private static final By METRIC_CARDS = By.cssSelector("#liveMetrics .metric");
    private static final By CERT_BADGE   = By.cssSelector("a.cert-badge");
    private static final By CONTACT_FORM = By.cssSelector("form[name='contact']");
    private static final By HONEYPOT     = By.cssSelector("input[name='bot-field']");
    private static final By BOOT_OVERLAY = By.id("boot");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open(String baseUrl) {
        driver.get(baseUrl + "/");
        // the boot overlay intercepts clicks while fading; wait until it is gone
        wait.until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOfElementLocated(BOOT_OVERLAY),
                ExpectedConditions.attributeContains(BOOT_OVERLAY, "class", "gone")
        ));
        return this;
    }

    // --- hero / structure ---
    public boolean heroIsVisible() {
        return visible(HERO_TITLE).isDisplayed();
    }

    public List<WebElement> navAnchors() {
        present(By.cssSelector(".nav"));
        return driver.findElements(NAV_LINKS);
    }

    public int metricCardCount() {
        visible(By.id("liveMetrics"));
        return driver.findElements(METRIC_CARDS).size();
    }

    // --- language switch ---
    public HomePage switchLanguage(String lang) {
        clickable(langButton(lang)).click();
        wait.until(ExpectedConditions.attributeContains(langButton(lang), "class", "active"));
        return this;
    }

    public String htmlLang() {
        return driver.findElement(By.tagName("html")).getAttribute("lang");
    }

    public String activeLangButton() {
        return driver.findElement(By.cssSelector(".lang-switch button.active"))
                     .getAttribute("data-lang");
    }

    public String storedLanguage() {
        Object value = ((JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem('qavant.lang');");
        return value == null ? null : value.toString();
    }

    private By langButton(String lang) {
        return By.cssSelector(".lang-switch button[data-lang='" + lang + "']");
    }

    // --- cert badge ---
    public WebElement certBadge() {
        return visible(CERT_BADGE);
    }

    // --- contact form ---
    public WebElement contactForm() {
        return present(CONTACT_FORM);
    }

    public WebElement honeypotField() {
        return present(HONEYPOT);
    }

    public WebElement formInput(String name) {
        return present(By.cssSelector("form[name='contact'] [name='" + name + "']"));
    }
}
