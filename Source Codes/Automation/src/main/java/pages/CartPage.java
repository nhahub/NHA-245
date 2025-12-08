package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.CartState;

import java.util.List;

public class CartPage {

    private WebDriver driver;

    private By cartIcon = By.id("shopping_cart_container");
    private By cartItem = By.className("cart_item");
    private By badge = By.className("shopping_cart_badge");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void viewCart() {
        driver.findElement(cartIcon).click();
    }

    public void removeItem(String id) {
        driver.findElement(By.id(id)).click();
        CartState.remove();
    }

    public boolean verifyCartBadge(WebDriver driver) {
        int expected = CartState.get();
        int actual = 0;

        List<WebElement> badges = this.driver.findElements(badge);
        if (!badges.isEmpty()) {
            try {
                actual = Integer.parseInt(badges.get(0).getText().trim());
            } catch (Exception ignored) {}
        }
        return actual == expected;
    }

    public int itemsCount() {
        return driver.findElements(cartItem).size();
    }
}
