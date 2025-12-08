package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import utils.CartState;

import java.util.Arrays;
import java.util.List;
public abstract class BaseTest {

    protected WebDriver driver;

    private final List<String> users = Arrays.asList(
            "standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"
    );

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        CartState.reset();
    }

    protected void login(String username) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @DataProvider(name = "usersProvider")
    public Object[] usersProvider() {
        return users.toArray();
    }
}
