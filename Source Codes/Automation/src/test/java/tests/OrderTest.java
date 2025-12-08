package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.OrderPage;
import pages.ProductsPage;

public class OrderTest extends BaseTest {

    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_CompleteSuccessfully(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();
        OrderPage order = new OrderPage(driver);
        Assert.assertTrue(order.confirmationDisplay());
    }

    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_ConfirmationElementsPresent(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();
        OrderPage order = new OrderPage(driver);
        boolean displayed = order.confirmationDisplay();

        Assert.assertTrue(displayed);
    }

    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_BackHomeNavigationWorks(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();
        OrderPage order = new OrderPage(driver);
        order.backHomeNavigation();
    }

    @Test(dataProvider = "usersProvider", groups = {"order"})
    public void TC_Order_BackHomeReturnsInventory(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.finishOrder();
        driver.findElement(org.openqa.selenium.By.id("back-to-products")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }
}
