package features;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Login helper + standalone runner.
 * Merges LoginPage behaviors (login form interactions) into this utility class.
 * This class can be executed directly (has main) or used from tests that rely on BaseClass.driver.
 */
public class Login {

    // Constants
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String DEFAULT_PASSWORD = "secret_sauce";

    // Test scenarios: username, password, description, expected result
    private static final Object[][] TEST_CASES = {
            {"standard_user", "secret_sauce", "Valid Standard User", "success"},
            {"locked_out_user", "secret_sauce", "Locked Out User", "Sorry, this user has been locked out."},
            {"problem_user", "secret_sauce", "Problem User", "success"},
            {"performance_glitch_user", "secret_sauce", "Performance Glitch User", "success"},
            {"error_user", "secret_sauce", "Error User", "success"},
            {"visual_user", "secret_sauce", "Visual User", "success"},
            {"", "", "Empty Username & Password", "Username is required"},
            {"standard_user", "", "Username Only", "Password is required"},
            {"", "secret_sauce", "Password Only", "Username is required"},
            {"standard_user", "wrong_password", "Wrong Password", "Username and password do not match any user"}
    };

    // Locators (mirrors LoginPage)
    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");


    public static void loginWithDriver(WebDriver driver, String username, String password) {
        WebElement usernameField = driver.findElement(USERNAME_FIELD);
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(PASSWORD_FIELD);
        passwordField.clear();
        passwordField.sendKeys(password);

        driver.findElement(LOGIN_BUTTON).click();
    }

    /**
     * Test all scenarios (standalone runner).
     */
    public static void runAllTests() {

        for (Object[] testCase : TEST_CASES) {
            String username = (String) testCase[0];
            String password = (String) testCase[1];
            String scenario = (String) testCase[2];
            String expectedResult = (String) testCase[3];

            testSingleScenario(username, password, scenario, expectedResult);
        }
    }

    /**
     * Test a single scenario (standalone runner).
     */
    public static void testSingleScenario(String username, String password, String scenario, String expectedResult) {

        WebDriver driver = null;

        try {
            System.out.println("\n========================================");
            System.out.println("TEST SCENARIO: " + scenario);
            System.out.println("========================================");

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Navigate
            driver.get(BASE_URL);
            System.out.println("✓ Opened Swag Labs website");

            // Step 2: Enter username
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
            usernameField.clear();
            usernameField.sendKeys(username);
            System.out.println("✓ Entered username: '" + username + "'");

            // Step 3: Enter password
            WebElement passwordField = driver.findElement(PASSWORD_FIELD);
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("✓ Entered password");

            // Step 4: Click login
            driver.findElement(LOGIN_BUTTON).click();
            System.out.println("✓ Clicked Login");

            Thread.sleep(1500); // wait for page load

            boolean loggedIn = driver.getCurrentUrl().contains("inventory.html");

            // Step 5: Check result
            if (expectedResult.equals("success")) {
                if (loggedIn) {
                    System.out.println("✔ PASSED: " + scenario + " - Login successful!");
                } else {
                    String actualError = driver.findElement(ERROR_MESSAGE).getText();
                    System.out.println("✘ FAILED: " + scenario + " - Unexpected login failure!");
                    System.out.println("Error: " + actualError);
                }
            } else {
                if (!loggedIn) {
                    String actualError = driver.findElement(ERROR_MESSAGE).getText();
                    if (actualError.contains(expectedResult)) {
                        System.out.println("✔ PASSED: " + scenario + " - Correct error shown: " + actualError);
                    } else {
                        System.out.println("✘ FAILED: " + scenario + " - Expected: '" + expectedResult + "', got: '" + actualError + "'");
                    }
                } else {
                    System.out.println("✘ FAILED: " + scenario + " - Login succeeded unexpectedly!");
                }
            }

        } catch (Exception e) {
            System.out.println("✘ ERROR in scenario '" + scenario + "': " + e.getMessage());
        } finally {
            if (driver != null) {
                try { Thread.sleep(1000); } catch (Exception ignored) {}
                driver.quit();
            }
        }
    }


}
