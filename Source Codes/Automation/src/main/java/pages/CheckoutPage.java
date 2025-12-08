package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.name("postalCode");
    private By continueBtn = By.id("continue");
    private By cancelBtn = By.id("cancel");
    private By itemTotal = By.className("summary_subtotal_label");
    private By tax = By.className("summary_tax_label");
    private By total = By.className("summary_total_label");
    private By finishBtn = By.id("finish");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToCheckout() {
        driver.findElement(By.id("checkout")).click();
    }

    public void continueWithEmptyFields() {
        driver.findElement(continueBtn).click();
    }

    public void continueWithOnlyFirstName() {
        driver.findElement(firstName).sendKeys("Nora");
        driver.findElement(continueBtn).click();
    }

    public void continueWithoutPostalCode() {
        driver.findElement(firstName).sendKeys("Nora");
        driver.findElement(lastName).sendKeys("Alaa");
        driver.findElement(continueBtn).click();
    }

    public void cancelFromStepOne() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cancel = wait.until(ExpectedConditions.elementToBeClickable(cancelBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancel);
        wait.until(ExpectedConditions.urlContains("cart"));
    }

    public void fillStepOneValidData() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        wait.until(ExpectedConditions.urlContains("checkout-step-one"));


        WebElement fName = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        fName.clear();
        fName.sendKeys("Nora");

        WebElement lName = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        lName.clear();
        lName.sendKeys("Alaa");

        WebElement zip = wait.until(ExpectedConditions.visibilityOfElementLocated(postalCode));
        zip.clear();
        zip.sendKeys("12345");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
        continueButton.click();


        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }



    public void validatePriceCalculation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

        String itemText = wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotal)).getText();
        String taxText = driver.findElement(tax).getText();
        String totalText = driver.findElement(total).getText();

        double items = Double.parseDouble(itemText.replaceAll("[^0-9.]", ""));
        double taxVal = Double.parseDouble(taxText.replaceAll("[^0-9.]", ""));
        double totalVal = Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));

        if (Math.abs((items + taxVal) - totalVal) > 0.01) {
            throw new AssertionError("Price calculation is incorrect");
        }
    }

    public void cancelFromStepTwo() {
        fillStepOneValidData();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(cancelBtn).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    public OrderPage finishOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement finish = wait.until(ExpectedConditions.elementToBeClickable(finishBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", finish);
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
        return new OrderPage(driver);
    }

    public void backHome() {
        driver.findElement(By.id("back-to-products")).click();
    }
}
