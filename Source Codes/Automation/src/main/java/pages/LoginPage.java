package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private static WebDriver driver;

    // Locators (same as your original file)
    private final By USERNAME_FIELD = By.id("user-name");
    private final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public static boolean isLoginButtonVisible() {
        return driver.findElement(LOGIN_BUTTON).isDisplayed();
    }




    public LoginPage enterUsername(String username) {
        WebElement field = driver.findElement(USERNAME_FIELD);
        field.clear();
        field.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement field = driver.findElement(PASSWORD_FIELD);
        field.clear();
        field.sendKeys(password);
        return this;
    }

    public ProductsPage clickLogin() {
        driver.findElement(LOGIN_BUTTON).click();
        return new ProductsPage(driver);
    }

    // Reusable login method
    public ProductsPage login(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }


    public boolean isLoggedIn() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    public String getErrorMessage() {
        if (driver.findElements(ERROR_MESSAGE).size() > 0) {
            return driver.findElement(ERROR_MESSAGE).getText();
        }
        return "";
    }
}
