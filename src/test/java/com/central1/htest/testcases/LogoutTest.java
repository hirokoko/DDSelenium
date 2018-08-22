package com.central1.htest.testcases;

import com.central1.htest.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class LogoutTest extends TestBase {

    @Test
    public void LogoutTest() throws InterruptedException, IOException {

        log.debug("Inside Logout Test");
        //driver.findElement(By.xpath(OR.getProperty("logoutLink_XPATH"))).click();
        click("logoutLink_XPATH");

        //soft failure
        //verifyEquals("abc", "xyz");

        Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("loginBtn_XPATH"))), "Logout not successful.");

        log.debug("Logged out successfully!");
        Reporter.log("Logged out.");

        //hard failure
        //Assert.fail("faking to fail");

        Thread.sleep(3000);
    }
}
