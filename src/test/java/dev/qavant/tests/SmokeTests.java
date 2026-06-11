package dev.qavant.tests;

import dev.qavant.core.BaseTest;
import dev.qavant.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTests extends BaseTest {

    @Test(description = "home page loads with the right title")
    public void homePageLoads() {
        new HomePage(driver).open(BASE_URL);
        Assert.assertTrue(driver.getTitle().contains("Qavant"),
                "title should contain 'Qavant' but was: " + driver.getTitle());
    }

    @Test(description = "hero headline is visible")
    public void heroIsVisible() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        Assert.assertTrue(home.heroIsVisible(), "hero headline should be visible");
    }

    @Test(description = "primary navigation exposes in-page anchors")
    public void navHasAnchors() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        Assert.assertTrue(home.navAnchors().size() >= 5,
                "expected at least 5 in-page nav anchors");
    }

    @Test(description = "live metrics widget renders all three cards")
    public void metricsWidgetHasThreeCards() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        Assert.assertEquals(home.metricCardCount(), 3,
                "metrics widget should render UI, API and Status cards");
    }
}
