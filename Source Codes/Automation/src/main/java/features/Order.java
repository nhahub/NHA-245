package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Order class (confirmation-focused).
 *
 * Contains ONLY:
 * - Order confirmation page validation
 * - Helper for validating complete page structure
 * - Navigation validation AFTER checkout is done (used in tests)
 *
 * Checkout flow is handled fully inside Checkout.java
 */
public class Order {

    /**
     * Check all elements on the Checkout Complete page:
     * - Title: "Checkout: Complete!"
     * - Message: "Thank you for your order!"
     * - Animation/Image: pony_express
     *
     * @return true if ALL required elements exist
     */
    public static boolean confirmationDisplay() {

        boolean titleCorrect = isElementPresent(
                By.xpath("//span[@class='title' and text()='Checkout: Complete!']")
        );

        boolean messageCorrect = isElementPresent(
                By.xpath("//h2[text()='Thank you for your order!']")
        );

        boolean imagePresent = isElementPresent(
                By.cssSelector("img.pony_express")
        );

        return titleCorrect && messageCorrect && imagePresent;
    }

    /**
     * After finishing order → click "Back Home" and ensure you reach inventory.
     * This is used in several of your scenarios.
     */
    public void backHomeNavigation() {

        // Click Back Home
        BaseClass.driver.findElement(By.id("back-to-products")).click();

        // Validate inventory opened
        boolean onInventory = BaseClass.driver.getCurrentUrl().contains("inventory.html");

        if (!onInventory) {
            throw new AssertionError("Back Home did NOT navigate to inventory!");
        }
    }

    /**
     * Utility: check element presence
     */
    public static boolean isElementPresent(By locator) {
        List<WebElement> elements = BaseClass.driver.findElements(locator);
        return !elements.isEmpty();
    }
}
