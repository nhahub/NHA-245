package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

/**
 * Handles the full process:
 * 1) User clicks "Checkout"
 * 2) Fills Step One info
 * 3) Goes to Step Two (overview)
 * 4) Clicks Finish
 * 5) Lands at Complete page
 * 6) Clicks Back Home
 *
 * Also includes negative steps needed by your scenarios.
 */
public class Checkout {

    /* --------------------------------------------------------------------- */
    /*                           PAGE TRANSITIONS                             */
    /* --------------------------------------------------------------------- */

    /** Cart → Checkout Step One */
    public void goToCheckout() {
        BaseClass.driver.findElement(By.id("checkout")).click();

        Assert.assertTrue(
                BaseClass.driver.getCurrentUrl().contains("checkout-step-one"),
                "Did NOT navigate to Checkout Step One!"
        );
    }

    /* --------------------------------------------------------------------- */
    /*                        CHECKOUT STEP ONE ACTIONS                       */
    /* --------------------------------------------------------------------- */

    /** Proceeds to Step One if not already there */
    private void ensureOnStepOne() {
        if (!BaseClass.driver.getCurrentUrl().contains("checkout-step-one")) {
            goToCheckout();
        }
    }

    /** Leaves all fields empty and clicks Continue (for negative tests) */
    public void continueWithEmptyFields() {
        ensureOnStepOne();
        BaseClass.driver.findElement(By.id("continue")).click();

        String msg = BaseClass.driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(
                msg.contains("Error: First Name is required"),
                "Expected First Name required error!"
        );
    }

    /** Fill only first name + click Continue */
    public void continueWithOnlyFirstName() {
        ensureOnStepOne();
        BaseClass.driver.findElement(By.id("first-name")).sendKeys("Nora");
        BaseClass.driver.findElement(By.id("continue")).click();

        String msg = BaseClass.driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(
                msg.contains("Error: Last Name is required"),
                "Expected Last Name required error!"
        );
    }

    /** Fill only first + last name and try continue */
    public void continueWithoutPostalCode() {
        ensureOnStepOne();
        BaseClass.driver.findElement(By.id("first-name")).sendKeys("Nora");
        BaseClass.driver.findElement(By.id("last-name")).sendKeys("Alaa");
        BaseClass.driver.findElement(By.id("continue")).click();

        String msg = BaseClass.driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(
                msg.contains("Error: Postal Code is required"),
                "Expected Postal Code required error!"
        );
    }

    /* --------------------------------------------------------------------- */
    /*                     NORMAL HAPPY PATH STEP ONE                         */
    /* --------------------------------------------------------------------- */

    /**
     * Fill all fields properly and go to Step Two (overview)
     */
    public void fillStepOneValidData() {
        ensureOnStepOne();
        BaseClass.driver.findElement(By.id("first-name")).sendKeys("Nora");
        BaseClass.driver.findElement(By.id("last-name")).sendKeys("Alaa");
        BaseClass.driver.findElement(By.id("postal-code")).sendKeys("12345");

        BaseClass.driver.findElement(By.id("continue")).click();

        Assert.assertTrue(
                BaseClass.driver.getCurrentUrl().contains("checkout-step-two"),
                "Checkout Step Two did NOT open!"
        );
    }

    /* --------------------------------------------------------------------- */
    /*                           CANCEL BUTTON TESTS                          */
    /* --------------------------------------------------------------------- */

    /** Cancel from Step One → returns to Cart */
    public void cancelFromStepOne() {
        ensureOnStepOne();
        BaseClass.driver.findElement(By.id("cancel")).click();

        Assert.assertTrue(
                BaseClass.driver.getCurrentUrl().contains("cart.html"),
                "Cancel did NOT return to cart!"
        );
    }

    /** Cancel from Step Two → returns to inventory */
    public void cancelFromStepTwo() {
        fillStepOneValidData(); // must be on step two
        BaseClass.driver.findElement(By.id("cancel")).click();

        Assert.assertTrue(
                BaseClass.driver.getCurrentUrl().contains("inventory.html"),
                "Cancel did NOT return to inventory!"
        );
    }

    /* --------------------------------------------------------------------- */
    /*                            STEP TWO (OVERVIEW)                         */
    /* --------------------------------------------------------------------- */

    /**
     * Validates that item total + tax = total.
     * Called inside some of your scenarios.
     */
    public void validatePriceCalculation() {
        String itemTotalText = BaseClass.driver.findElement(By.className("summary_subtotal_label")).getText();
        String taxText = BaseClass.driver.findElement(By.className("summary_tax_label")).getText();
        String totalText = BaseClass.driver.findElement(By.className("summary_total_label")).getText();

        double itemTotal = Double.parseDouble(itemTotalText.replaceAll("[^0-9.]", ""));
        double tax = Double.parseDouble(taxText.replaceAll("[^0-9.]", ""));
        double total = Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));

        Assert.assertEquals(
                itemTotal + tax, total, 0.01,
                "The Total price calculation mismatch!"
        );
    }

    /* --------------------------------------------------------------------- */
    /*                            FINISH ORDER FLOW                           */
    /* --------------------------------------------------------------------- */

    /** Click Finish → goes to Complete page */
    public void finishOrder() {
        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(5));

        WebElement finishBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("finish"))
        );

        finishBtn.click();

        wait.until(ExpectedConditions.urlContains("checkout-complete"));

        Assert.assertTrue(
                BaseClass.driver.getCurrentUrl().contains("checkout-complete"),
                "Finish did NOT navigate to Checkout Complete!"
        );

        // Validate header
        WebElement header = BaseClass.driver.findElement(By.className("complete-header"));
        Assert.assertTrue(
                header.getText().contains("Thank you for your order!"),
                "Thank-you header not found!"
        );
    }


    /** Back Home button from complete page → inventory */
    public void backHome() {
        BaseClass.driver.findElement(By.id("back-to-products")).click();

        Assert.assertTrue(
                BaseClass.driver.getCurrentUrl().contains("inventory.html"),
                "Back Home did NOT navigate to inventory!"
        );
    }

    /* --------------------------------------------------------------------- */
    /*                     COMBINED HAPPY PATH HELPER                         */
    /* --------------------------------------------------------------------- */

    /**
     * Run the FULL happy path:
     * Cart → Checkout → Step One → Step Two → Finish → Complete → Back Home
     */
    public void fullCheckoutFlow() {
        goToCheckout();
        fillStepOneValidData();
        validatePriceCalculation();
        finishOrder();
        backHome();
    }
}
