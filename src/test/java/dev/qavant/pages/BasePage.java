package dev.qavant.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Explicit waits only — no Thread.sleep() anywhere in this codebase.
 * Every action waits for exactly the condition it needs.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement present(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Scrolls an element into the viewport the way a user would.
     * The site reveals below-the-fold content via IntersectionObserver
     * (elements start at opacity 0), so anything outside the initial
     * viewport must be scrolled to BEFORE waiting for visibility.
     */
    protected WebElement scrollTo(By locator) {
        WebElement el = present(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
