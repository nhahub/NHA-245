package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.LogoutPage;

public class LogoutTest extends BaseTest {

    @Test(dataProvider = "usersProvider", groups = {"logout"})
    public void TC_Logout_Works(String user) {

        LoginPage login = new LoginPage(driver);

        login(user);

        LogoutPage logout = new LogoutPage(driver);
        LoginPage loginPage = logout.logout();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
                "User was NOT redirected to login after logout!");

        Assert.assertTrue(loginPage.isLoginButtonVisible(),
                "Login button is NOT visible after logout!");
    }
}
