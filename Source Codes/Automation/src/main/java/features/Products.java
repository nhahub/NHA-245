package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Products class (merged InventoryPage + product helpers).
 * Handles adding items, sorting, reading prices/names, and opening products.
 * Uses BaseClass.driver (shared driver model).
 */
public class Products {

    // Locators originally from InventoryPage
    private final By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private final By sortDropdown = By.cssSelector(".product_sort_container");

    public Products() {
        // Empty constructor - uses BaseClass.driver internally
    }

    /* ------------------------------------------------------------ */
    /*                    ADD ITEMS TO CART                         */
    /* ------------------------------------------------------------ */

    /** Add the first product in the inventory */
    public void addSingleItem() {
        BaseClass.driver.findElements(addToCartButtons).get(0).click();
        CartState.add();
    }

    /** Add a specific number of products */
    public void addMultipleItems(int count) {
        List<WebElement> items = BaseClass.driver.findElements(addToCartButtons);

        for (int i = 0; i < count && i < items.size(); i++) {
            items.get(i).click();
            CartState.add();
        }
    }

    /**
     * Add ALL products on the page.
     * Your tests call addMultipleItems() without parameters → this version needed.
     */
    public void addMultipleItems() {
        List<WebElement> items = BaseClass.driver.findElements(By.cssSelector("button[id^='add-to-cart']"));
        for (WebElement btn : items) {
            btn.click();
            CartState.add();
        }
    }

    /* ------------------------------------------------------------ */
    /*                      PRODUCT INFORMATION                     */
    /* ------------------------------------------------------------ */

    /** Extract product price list as doubles */
    public List<Double> getProductPrices() {
        List<WebElement> priceElements =
                BaseClass.driver.findElements(By.className("inventory_item_price"));

        List<Double> prices = new ArrayList<>();
        for (WebElement price : priceElements) {
            prices.add(Double.parseDouble(price.getText().replace("$", "").trim()));
        }
        return prices;
    }

    /** Extract product names */
    public List<String> getProductNames() {
        List<WebElement> nameElements =
                BaseClass.driver.findElements(By.className("inventory_item_name"));

        List<String> names = new ArrayList<>();
        for (WebElement name : nameElements) {
            names.add(name.getText().trim());
        }
        return names;
    }

    /* ------------------------------------------------------------ */
    /*                 PRODUCT SORTING & NAVIGATION                 */
    /* ------------------------------------------------------------ */

    /** Sort products via dropdown */
    public void sortProducts(String visibleText) {
        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(10));
        WebElement dropdown =
                wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));

        new Select(dropdown).selectByVisibleText(visibleText);
    }

    /** Open a product directly by its element ID */
    public void openProductById(String id) {
        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(10));
        WebElement product =
                wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));

        product.click();
    }
}
