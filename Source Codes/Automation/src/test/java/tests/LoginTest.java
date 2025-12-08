package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", "Valid Standard User", "success"},
                {"locked_out_user", "secret_sauce", "Locked Out User", "Sorry, this user has been locked out."},
                {"problem_user", "secret_sauce", "Problem User", "success"},
                {"performance_glitch_user", "secret_sauce", "Performance Glitch User", "success"},
                {"error_user", "secret_sauce", "Error User", "success"},
                {"visual_user", "secret_sauce", "Visual User", "success"},
                {"", "", "Empty Username & Password", "Username is required"},
                {"standard_user", "", "Username Only", "Password is required"},
                {"", "secret_sauce", "Password Only", "Username is required"},
                {"standard_user", "wrong_password", "Wrong Password", "do not match any user"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginScenarioTest(String username, String password, String scenario, String expectedResult) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username)
                .enterPassword(password)
                .clickLogin();

        boolean success = loginPage.isLoggedIn();

        if (expectedResult.equals("success")) {
            Assert.assertTrue(success, "Expected login success but failed!");
        } else {
            Assert.assertFalse(success, "Login succeeded unexpectedly!");
            Assert.assertTrue(
                    loginPage.getErrorMessage().contains(expectedResult),
                    "Error message mismatch!"
            );
        }
    }
}
