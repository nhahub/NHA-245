package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.OrderPage;
import pages.ProductsPage;

public class FullJourneyTest extends BaseTest {

    @Test(dataProvider = "usersProvider", groups = {"full_journey"})
    public void TC_FullUserJourney(String user) {

        login(user);
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();
        Assert.assertEquals(cart.itemsCount(), 1);

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.validatePriceCalculation();

        OrderPage order = checkout.finishOrder();
        Assert.assertTrue(order.confirmationDisplay());

        order.backHomeNavigation();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "User did NOT return to inventory after order completion"
        );
    }
}
