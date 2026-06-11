package dev.qavant.tests;

import dev.qavant.core.BaseTest;
import dev.qavant.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class I18nTests extends BaseTest {

    @Test(description = "active language button matches the document language on first visit")
    public void activeLanguageIsConsistent() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        // the app detects the browser language; we assert internal consistency,
        // not a hard-coded locale (same contract as the Playwright suite)
        Assert.assertEquals(home.activeLangButton(), home.htmlLang(),
                "active lang button must match <html lang>");
    }

    @Test(description = "switching to German changes the document language")
    public void switchToGerman() {
        HomePage home = new HomePage(driver).open(BASE_URL).switchLanguage("de");
        Assert.assertEquals(home.htmlLang(), "de");
    }

    @Test(description = "language choice is persisted to localStorage")
    public void languagePersists() {
        HomePage home = new HomePage(driver).open(BASE_URL).switchLanguage("de");
        Assert.assertEquals(home.storedLanguage(), "de",
                "chosen language should be stored for the next visit");
    }
}
