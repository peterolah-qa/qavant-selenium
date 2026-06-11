package dev.qavant.tests;

import dev.qavant.core.BaseTest;
import dev.qavant.pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CertBadgeTests extends BaseTest {

    /**
     * We assert the link CONTRACT (where it points and how it opens), not the
     * destination itself — a third party's uptime is not this suite's concern.
     */
    @Test(description = "ISTQB badge is a verifiable link to the official register")
    public void badgeLinksToRegister() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        WebElement badge = home.certBadge();

        Assert.assertTrue(badge.getText().contains("ISTQB"), "badge should mention ISTQB");
        Assert.assertTrue(badge.getAttribute("href").startsWith("https://scr.istqb.org"),
                "badge must point at the official Successful Candidate Register");
        Assert.assertEquals(badge.getAttribute("target"), "_blank");
        Assert.assertTrue(badge.getAttribute("rel").contains("noopener"),
                "external link must carry rel=noopener");
    }
}
