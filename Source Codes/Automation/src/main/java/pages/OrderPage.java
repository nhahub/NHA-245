package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {

    private WebDriver driver;

    private By title = By.xpath("//span[@class='title' and text()='Checkout: Complete!']");
    private By message = By.xpath("//h2[text()='Thank you for your order!']");
    private By image = By.cssSelector("img.pony_express");
    private By backHomeBtn = By.id("back-to-products");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean confirmationDisplay() {
        boolean t = !driver.findElements(title).isEmpty();
        boolean m = !driver.findElements(message).isEmpty();
        boolean i = !driver.findElements(image).isEmpty();
        return t && m && i;
    }

    public void backHomeNavigation() {
        driver.findElement(backHomeBtn).click();
    }
}
