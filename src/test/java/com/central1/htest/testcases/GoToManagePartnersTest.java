package com.central1.htest.testcases;

import com.central1.htest.base.TestBase;
import com.central1.htest.utilities.TestUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;

public class GoToManagePartnersTest extends TestBase {

    @Test
    public void goToManagePartnersTest() throws InterruptedException, IOException {

        //run mode
        if(!TestUtil.isTestRunnable("goToManagePartnersTest", excel))
            throw new SkipException("Skipping the test goToManagePartnersTest as the Runmode is NO");


        log.debug("Inside GoToManagePartners Test");

        //driver.findElement(By.xpath(OR.getProperty("navAccountServices_XPATH"))).click();
        click("navAccountServices_XPATH");

        //soft failure for Conexus
        //verifyEquals("Manage Partners Accounts", driver.findElement(By.xpath(OR.getProperty("itemMngPartners_XPATH"))).getText());


        //driver.findElement(By.xpath(OR.getProperty("navMngPartners_XPATH"))).click();
        click("navMngPartners_XPATH");


        Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("titleMngPartners_XPATH"))), "Could not go to Manage Partners page.");

        //Thread.sleep(3000);


    }
}
