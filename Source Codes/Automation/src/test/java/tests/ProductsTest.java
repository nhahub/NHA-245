package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductsPage;

import java.util.List;

public class ProductsTest extends BaseTest {

    /* ---------------------- ADD SINGLE ITEM ---------------------- */
    @Test(dataProvider = "usersProvider", groups = {"products"}, testName = "TC_Products_AddSingleItem")
    public void TC_Products_AddSingleItem(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        int count = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertEquals(count, 1, "Cart should contain exactly 1 item!");
    }

    /* ---------------------- ADD MULTIPLE ITEMS ---------------------- */
    @Test(dataProvider = "usersProvider", groups = {"products"}, testName = "TC_Products_AddMultipleItems")
    public void TC_Products_AddMultipleItems(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addMultipleItems();

        CartPage cart = new CartPage(driver);
        cart.viewCart();

        int count = driver.findElements(org.openqa.selenium.By.className("cart_item")).size();
        Assert.assertTrue(count > 1, "Cart should contain multiple items!");
    }

    /* ---------------------- SORT HIGH → LOW ---------------------- */
    @Test(dataProvider = "usersProvider", groups = {"products"}, testName = "TC_Products_SortHighToLow")
    public void TC_Products_SortHighToLow(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.sortProducts("Price (high to low)");

        List<Double> prices = products.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(
                    prices.get(i) >= prices.get(i + 1),
                    "Sorting High → Low failed at index " + i
            );
        }
    }

    /* ---------------------- SORT LOW → HIGH ---------------------- */
    @Test(dataProvider = "usersProvider", groups = {"products"}, testName = "TC_Products_SortLowToHigh")
    public void TC_Products_SortLowToHigh(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.sortProducts("Price (low to high)");

        List<Double> prices = products.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(
                    prices.get(i) <= prices.get(i + 1),
                    "Sorting Low → High failed at index " + i
            );
        }
    }

    /* ---------------------- SORT Z → A ---------------------- */
    @Test(dataProvider = "usersProvider", groups = {"products"}, testName = "TC_Products_SortZtoA")
    public void TC_Products_SortZtoA(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.sortProducts("Name (Z to A)");

        List<String> names = products.getProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(
                    names.get(i).compareTo(names.get(i + 1)) >= 0,
                    "Sorting Z → A failed at index " + i
            );
        }
    }

    /* ---------------------- OPEN PRODUCT DETAILS ---------------------- */
    @Test(dataProvider = "usersProvider", groups = {"products"}, testName = "TC_Products_OpenSpecificProduct")
    public void TC_Products_OpenSpecificProduct(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.openProductById("item_4_title_link");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory-item"),
                "Did NOT open product details page!"
        );
    }
}
