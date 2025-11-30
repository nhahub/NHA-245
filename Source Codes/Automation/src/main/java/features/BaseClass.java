package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Base class for all tests.
 * Contains the shared WebDriver instance and login helper.
 */
public abstract class BaseClass {

    // Shared static driver across all classes
    public static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
    }

    protected void login(String username) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }
    @BeforeMethod
    public void resetCartState() {
        CartState.reset();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Users used for data provider
    private final List<String> users = Arrays.asList(
            "standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"
    );

    @DataProvider(name = "usersProvider")
    public Object[] usersProvider() {
        return users.toArray();
    }
}
