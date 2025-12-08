package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CartState;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage {

    private WebDriver driver;

    private By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private By sortDropdown = By.cssSelector(".product_sort_container");
    private By priceElements = By.className("inventory_item_price");
    private By nameElements = By.className("inventory_item_name");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addSingleItem() {
        driver.findElements(addToCartButtons).get(0).click();
        CartState.add();
    }

    public void addMultipleItems() {
        List<WebElement> items = driver.findElements(addToCartButtons);
        for (WebElement btn : items) {
            btn.click();
            CartState.add();
        }
    }

    public void addMultipleItems(int count) {
        List<WebElement> items = driver.findElements(addToCartButtons);
        for (int i = 0; i < count && i < items.size(); i++) {
            items.get(i).click();
            CartState.add();
        }
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement price : driver.findElements(priceElements)) {
            prices.add(Double.parseDouble(price.getText().replace("$", "").trim()));
        }
        return prices;
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement name : driver.findElements(nameElements)) {
            names.add(name.getText().trim());
        }
        return names;
    }

    public void sortProducts(String visibleText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdown));
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public void openProductById(String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        product.click();
    }
}
