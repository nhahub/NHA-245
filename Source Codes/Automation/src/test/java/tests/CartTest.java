package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductsPage;

import java.util.List;

public class CartTest extends BaseTest {

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_OpenCartPage(String user) {
        login(user);
        CartPage cart = new CartPage(driver);
        cart.viewCart();
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_HeaderIsCorrect(String user) {
        login(user);
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        String header = driver.findElement(By.className("title")).getText();
        Assert.assertEquals(header, "Your Cart");
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_EmptyCart(String user) {
        login(user);
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        int itemCount = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemCount, 0);
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_ViewWithItems(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addMultipleItems();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        int items = driver.findElements(By.className("cart_item")).size();
        Assert.assertTrue(items > 0);
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_RemoveOneItem(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        WebElement removeBtn = driver.findElement(By.cssSelector("button[id^='remove']"));
        String id = removeBtn.getAttribute("id");
        cart.removeItem(id);
        int items = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(items, 0);
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_RemoveAllItems(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addMultipleItems();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        List<WebElement> buttons = driver.findElements(By.cssSelector("button[id^='remove']"));
        for (WebElement b : buttons) {
            cart.removeItem(b.getAttribute("id"));
        }
        int finalCount = driver.findElements(By.className("cart_item")).size();
        Assert.assertEquals(finalCount, 0);
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_BadgeUpdatesCorrectly(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addMultipleItems();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        boolean actual = cart.verifyCartBadge(driver);
        List<String> failing = List.of("locked_out_user", "error_user", "problem_user");
        if (failing.contains(user)) {
            Assert.assertFalse(actual);
        } else {
            Assert.assertTrue(actual);
        }
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_BadgeDisappearsAtZero(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        WebElement removeBtn = driver.findElement(By.cssSelector("button[id^='remove']"));
        String id = removeBtn.getAttribute("id");
        cart.removeItem(id);
        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));
        Assert.assertTrue(badge.isEmpty());
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_PersistsAfterNavigation(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addMultipleItems();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        driver.navigate().back();
        driver.navigate().forward();
        int items = driver.findElements(By.className("cart_item")).size();
        Assert.assertTrue(items > 0);
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_ContinueShopping(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        driver.findElement(By.id("continue-shopping")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test(dataProvider = "usersProvider", groups = {"cart"})
    public void TC_Cart_CheckoutButton(String user) {
        login(user);
        ProductsPage products = new ProductsPage(driver);
        products.addSingleItem();
        CartPage cart = new CartPage(driver);
        cart.viewCart();
        driver.findElement(By.id("checkout")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
    }
}
