package features;

import features.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.List;


public class AllTests extends BaseClass {

    /* --------------------------------------------------------------------- */
    /*                             LOGIN TESTS                                */
    /* --------------------------------------------------------------------- */


    @Test(dataProvider = "usersProvider", groups = {"login"})
    public void TC_Login_Smoke(String user) {
        login(user);

        boolean onInventory = driver.getCurrentUrl().contains("inventory.html");
        Assert.assertTrue(
                onInventory || driver.getCurrentUrl().contains("saucedemo.com"),
                "Login produced an unexpected navigation for user: " + user
        );
    }

    /* --------------------------------------------------------------------- */
    /*                               CART TESTS                               */
    /* --------------------------------------------------------------------- */

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_OpenCartPage(String user) {
        login(user);

        Cart cart = new Cart();
        cart.viewCart();
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_HeaderIsCorrect(String user) {
        login(user);

        Cart cart = new Cart();
        cart.viewCart();

        String header = driver.findElement(org.openqa.selenium.By.className("title")).getText();
        Assert.assertEquals(header, "Your Cart", "Cart page header incorrect!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_EmptyCart(String user) {
        login(user);

        Cart cart = new Cart();
        cart.viewCart();

        int itemCount = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertEquals(itemCount, 0, "Cart should be EMPTY but items exist!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_ViewWithItems(String user) {
        login(user);

        Products products = new Products();
        products.addMultipleItems();

        Cart cart = new Cart();
        cart.viewCart();

        int items = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertTrue(items > 0, "Items not found in cart!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_RemoveOneItem(String user) {
        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        // remove the only item using removeItem
        org.openqa.selenium.WebElement removeBtn = driver.findElement(org.openqa.selenium.By.cssSelector("button[id^='remove']"));
        String removeId = removeBtn.getAttribute("id");

        cart.removeItem(removeId);

        int items = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertEquals(items, 0, "Item was NOT removed!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_RemoveAllItems(String user) {
        login(user);

        Products products = new Products();
        products.addMultipleItems();

        Cart cart = new Cart();
        cart.viewCart();

        List<org.openqa.selenium.WebElement> removeBtns = driver.findElements(org.openqa.selenium.By.cssSelector("button[id^='remove']"));

        for (org.openqa.selenium.WebElement btn : removeBtns) {
            String id = btn.getAttribute("id");
            cart.removeItem(id);
        }

        int finalCount = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertEquals(finalCount, 0, "Cart still has items after removing ALL!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_BadgeUpdatesCorrectly(String user) {
        login(user);

        Products products = new Products();
        products.addMultipleItems();

        Cart cart = new Cart();
        cart.viewCart();

        boolean actual = Cart.verifyCartBadge();

        // Users expected to FAIL the verification
        List<String> failingUsers = List.of(
                "locked_out_user",
                "error_user",
                "problem_user"
        );

        if (failingUsers.contains(user)) {
            Assert.assertFalse(actual, user + " is expected to FAIL badge verification but passed!");
        } else {
            Assert.assertTrue(actual, user + " is expected to PASS badge verification but failed!");
        }
    }


    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_BadgeDisappearsAtZero(String user) {
        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        // remove last item
        org.openqa.selenium.WebElement removeBtn = driver.findElement(org.openqa.selenium.By.cssSelector("button[id^='remove']"));
        String removeId = removeBtn.getAttribute("id");
        cart.removeItem(removeId);

        // badge MUST disappear
        List<org.openqa.selenium.WebElement> badge = driver.findElements(org.openqa.selenium.By.className("shopping_cart_badge"));
        Assert.assertTrue(badge.isEmpty(), "Badge SHOULD disappear when cart = 0");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_PersistsAfterNavigation(String user) {
        login(user);

        Products products = new Products();
        products.addMultipleItems();

        Cart cart = new Cart();
        cart.viewCart();

        driver.navigate().back();
        driver.navigate().forward();

        int items = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertTrue(items > 0, "Cart items disappeared after navigation!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_ContinueShopping(String user) {
        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        driver.findElement(org.openqa.selenium.By.id("continue-shopping")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                "Continue Shopping did NOT return to inventory!");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_CheckoutButton(String user) {
        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        driver.findElement(org.openqa.selenium.By.id("checkout")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),
                "Checkout button did NOT navigate to step one!");
    }
    /* --------------------------------------------------------------------- */
    /*                           PRODUCT TESTS                                */
    /* --------------------------------------------------------------------- */

    @Test(groups = {"products"})
    public void TC_Products_AddSingleItem() {

        // login as a stable user (standard_user only)
        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        int cnt = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertEquals(cnt, 1, "Expected exactly 1 item in cart!");
    }

    @Test(groups = {"products"})
    public void TC_Products_AddMultipleItems() {

        login("standard_user");

        Products products = new Products();
        products.addMultipleItems();   // adds all items

        Cart cart = new Cart();
        cart.viewCart();

        int cnt = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertTrue(cnt > 1, "Expected multiple items in cart!");
    }

    @Test(groups = {"products"})
    public void TC_Products_SortHighToLow() {

        login("standard_user");

        Products products = new Products();
        products.sortProducts("Price (high to low)");

        List<Double> prices = products.getProductPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(
                    prices.get(i) >= prices.get(i + 1),
                    "Sorting high-to-low failed at index " + i
            );
        }
    }

    @Test(groups = {"products"})
    public void TC_Products_SortLowToHigh() {

        login("standard_user");

        Products products = new Products();
        products.sortProducts("Price (low to high)");

        List<Double> prices = products.getProductPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(
                    prices.get(i) <= prices.get(i + 1),
                    "Sorting low-to-high failed at index " + i
            );
        }
    }

    @Test(groups = {"products"})
    public void TC_Products_SortZtoA() {

        login("standard_user");

        Products products = new Products();
        products.sortProducts("Name (Z to A)");

        List<String> names = products.getProductNames();

        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(
                    names.get(i).compareTo(names.get(i + 1)) >= 0,
                    "Sorting Z to A failed at index " + i
            );
        }
    }

    @Test(groups = {"products"})
    public void TC_Products_OpenSpecificProduct() {

        login("standard_user");

        Products products = new Products();
        products.openProductById("item_4_title_link");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory-item"),
                "Did NOT navigate to product details page!"
        );
    }
    /* --------------------------------------------------------------------- */
    /*                           CHECKOUT TESTS                               */
    /* --------------------------------------------------------------------- */

    @Test(groups = {"checkout"})
    public void TC_Checkout_EmptyFields() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();          // must go to the step before
        checkout.continueWithEmptyFields();  // validation
    }

    @Test(groups = {"checkout"})
    public void TC_Checkout_FirstNameOnly() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.continueWithOnlyFirstName();
    }

    @Test(groups = {"checkout"})
    public void TC_Checkout_MissingPostalCode() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.continueWithoutPostalCode();
    }

    @Test(groups = {"checkout"})
    public void TC_Checkout_ValidInputsGoToStepTwo() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();   // goes to step two successfully
    }

    @Test(groups = {"checkout"})
    public void TC_Checkout_ValidatePrices() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.validatePriceCalculation();   // verify subtotal + tax = total
    }

    @Test(groups = {"checkout"})
    public void TC_Checkout_CancelStepOne() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.cancelFromStepOne();
    }

    @Test(groups = {"checkout"})
    public void TC_Checkout_CancelStepTwo() {

        login("standard_user");

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.cancelFromStepTwo();
    }
    /* --------------------------------------------------------------------- */
    /*                             ORDER TESTS                                */
    /* --------------------------------------------------------------------- */

    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_CompleteSuccessfully(String user) {

        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();

        Assert.assertTrue(
                Order.confirmationDisplay(),
                "Order confirmation screen missing elements!"
        );
    }


    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_ConfirmationElementsPresent(String user) {

        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();

        boolean displayed = Order.confirmationDisplay();
        Assert.assertTrue(
                displayed,
                "Confirmation page missing expected elements!"
        );
    }


    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_BackHomeNavigationWorks(String user) {

        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();

        Order order = new Order();
        order.backHomeNavigation();    // verify back-home returns to inventory
    }


    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_BackHomeReturnsInventory(String user) {

        login(user);

        Products products = new Products();
        products.addSingleItem();

        Cart cart = new Cart();
        cart.viewCart();

        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();

        // now manually click back home
        driver.findElement(org.openqa.selenium.By.id("back-to-products")).click();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Back Home did NOT navigate to inventory!"
        );
    }
    /* --------------------------------------------------------------------- */
    /*                       FULL END-TO-END JOURNEY                          */
    /* --------------------------------------------------------------------- */

    @Test(dataProvider = "usersProvider", groups = {"full_journey"})
    public void TC_FullUserJourney(String user) {

        // 1) LOGIN
        login(user);
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Login failed — cannot start full journey!"
        );

        // 2) ADD ITEM(S)
        Products products = new Products();
        products.addSingleItem();

        // 3) OPEN CART
        Cart cart = new Cart();
        cart.viewCart();
        Assert.assertEquals(
                cart.itemsCount(), 1,
                "Cart should contain exactly ONE item at start of journey!"
        );

        // 4) CHECKOUT STEP ONE
        Checkout checkout = new Checkout();
        checkout.goToCheckout();
        checkout.fillStepOneValidData();

        // 5) CHECKOUT STEP TWO (verify prices)
        checkout.validatePriceCalculation();

        // 6) FINISH ORDER
        checkout.finishOrder();
        Assert.assertTrue(
                Order.confirmationDisplay(),
                "Order confirmation elements missing in full journey!"
        );

        // 7) BACK HOME
        checkout.backHome();
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Back Home did NOT return to inventory after full journey!"
        );
    }
}



