package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductsPage;

public class CheckoutTest extends BaseTest {

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_EmptyFields(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.continueWithEmptyFields();
    }

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_FirstNameOnly(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.continueWithOnlyFirstName();
    }

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_MissingPostalCode(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.continueWithoutPostalCode();
    }

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_ValidInputsGoToStepTwo(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
    }

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_ValidatePrices(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.fillStepOneValidData();
        checkout.validatePriceCalculation();
    }

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_CancelStepOne(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.cancelFromStepOne();
    }

    @Test(dataProvider = "usersProvider", groups = {"checkout"})
    public void TC_Checkout_CancelStepTwo(String user) {
        login(user);

        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.goToCheckout();
        checkout.cancelFromStepTwo();
    }
}
