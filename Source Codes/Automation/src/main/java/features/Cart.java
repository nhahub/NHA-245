package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

/**
 * Cart class (fully merged).
 * Contains ALL cart-related functionality:
 * - Viewing cart
 * - Removing items
 * - Checking badge
 * - Checking item count
 * - Cart persistence helpers
 *
 * All scenario-level tests go in AllTests.java.
 */
public class Cart extends BaseClass {

    /* ------------------------------------------------------------ */
    /*                       BASIC CART ACTIONS                     */
    /* ------------------------------------------------------------ */

    /** Clicks the cart icon and asserts cart page opens */
    public void viewCart() {
        driver.findElement(By.id("shopping_cart_container")).click();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart.html"),
                "Cart page did NOT open!"
        );
    }

    /** Removes a specific item by button ID */
    public void removeItem(String productID) {
        driver.findElement(By.id(productID)).click();

        // After clicking remove, the element MUST disappear
        boolean removed = driver.findElements(By.id(productID)).isEmpty();
        Assert.assertTrue(removed, "Item with ID '" + productID + "' was NOT removed!");
    }

    /** Removes ALL items currently in the cart */
    public void removeAllItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By removeBtnSelector = By.cssSelector("button[id^='remove']");

        while (!driver.findElements(removeBtnSelector).isEmpty()) {

            WebElement btn = driver.findElement(removeBtnSelector);
            String id = btn.getAttribute("id");

            removeItem(id);

            // Wait for this ID to disappear to avoid stale elements
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
        }
    }


    /* ------------------------------------------------------------ */
    /*                           BADGE CHECKS                       */
    /* ------------------------------------------------------------ */

    /** Verifies badge count vs CartState count */
    public static boolean verifyCartBadge() {
        int expected = CartState.get();
        int actual = 0;

        boolean exists = isElementPresent(driver, By.className("shopping_cart_badge"));

        if (exists) {
            try {
                actual = Integer.parseInt(
                        driver.findElement(By.className("shopping_cart_badge"))
                                .getText().trim()
                );
            } catch (Exception e) {
                actual = 0;
            }
        }

        return actual == expected;
    }

    /** Badge should disappear when count = 0 */
    public boolean isBadgeInvisible() {
        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));
        return badge.isEmpty();
    }

    /* ------------------------------------------------------------ */
    /*                      CART ITEM INFORMATION                   */
    /* ------------------------------------------------------------ */

    /** Returns number of items currently in cart */
    public int itemsCount() {
        return driver.findElements(By.className("cart_item")).size();
    }

    /** Returns TRUE if element exists */
    public static boolean isElementPresent(WebElement element) { return element != null; }

    public static boolean isElementPresent(org.openqa.selenium.WebDriver d, By by) {
        List<WebElement> elements = d.findElements(by);
        return !elements.isEmpty();
    }

    /* ------------------------------------------------------------ */
    /*                     NAVIGATION VALIDATIONS                   */
    /* ------------------------------------------------------------ */

    /** Used in tests that check cart persistence after navigation */
    public boolean itemsPersistAfterNavigation() {
        int before = itemsCount();
        driver.navigate().back();
        driver.navigate().forward();
        int after = itemsCount();
        return after == before && after > 0;
    }

    /* ------------------------------------------------------------ */
    /*                    CONTINUE SHOPPING / CHECKOUT              */
    /* ------------------------------------------------------------ */

    /** Clicks Continue Shopping button */
    public void continueShopping() {
        driver.findElement(By.id("continue-shopping")).click();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Continue Shopping did NOT return to inventory!"
        );
    }

    /** Clicks checkout to go to checkout step 1 */
    public void proceedToCheckout() {
        driver.findElement(By.id("checkout")).click();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one"),
                "Checkout button did NOT navigate to step one!"
        );
    }
}
