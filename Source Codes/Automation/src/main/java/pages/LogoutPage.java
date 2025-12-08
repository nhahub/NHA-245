package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By menuBtn = By.id("react-burger-menu-btn");
    private By logoutBtn = By.id("logout_sidebar_link");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public LoginPage logout() {

        //First, make sure we are on inventory page
        if (!driver.getCurrentUrl().contains("inventory")) {
            throw new IllegalStateException("Logout cannot be performed: User is not on inventory page");
        }

        //Click menu safely
        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(menuBtn)
        );
        menu.click();

        //Wait until logout is VISIBLE (not just clickable)
        WebElement logout = wait.until(
                ExpectedConditions.visibilityOfElementLocated(logoutBtn)
        );

        //JavaScript click to avoid animation overlay issues
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", logout);

        //Wait until redirected back to login page
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));

        return new LoginPage(driver);
    }

}
