package dev.qavant.tests;

import dev.qavant.core.BaseTest;
import dev.qavant.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactFormTests extends BaseTest {

    @Test(description = "contact form is a POST form discoverable by Netlify")
    public void formIsNetlifyPost() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        Assert.assertEquals(home.contactForm().getAttribute("method").toLowerCase(), "post");
        Assert.assertEquals(home.contactForm().getAttribute("name"), "contact");
    }

    @Test(description = "required fields are enforced via HTML5 validation")
    public void requiredFieldsPresent() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        Assert.assertNotNull(home.formInput("name").getAttribute("required"));
        Assert.assertNotNull(home.formInput("email").getAttribute("required"));
        Assert.assertEquals(home.formInput("email").getAttribute("type"), "email",
                "email input must use type=email for built-in validation");
    }

    @Test(description = "honeypot field exists but is hidden from real users")
    public void honeypotIsHidden() {
        HomePage home = new HomePage(driver).open(BASE_URL);
        Assert.assertFalse(home.honeypotField().isDisplayed(),
                "bot-field must not be visible to humans");
    }
}
