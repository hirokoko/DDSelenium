package com.central1.htest.testcases;

import com.central1.htest.base.TestBase;

import com.central1.htest.utilities.TestUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Hashtable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class CheckAccountActivityTest extends TestBase {

    @Test //(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void CheckAccountActivityTest() throws InterruptedException, IOException {

        //data provider parameter for the above Hashtable <String,String> data is removed

        //run mode
        if(!TestUtil.isTestRunnable("CheckAccountActivityTest", excel))
            throw new SkipException("Skipping the test CheckAccountActivityTest as the Runmode is NO");



        log.debug("Inside CheckAccountActivity Test");

        //driver.findElement(By.xpath(OR.getProperty("navAccountActivity_XPATH"))).click();
        click("navAccountActivity_XPATH");

        verifyEquals("Account Activity", driver.findElement(By.xpath(OR.getProperty("titleAcctActivity_XPATH"))).getText());

        //select("acctDropDown_XPATH", data.get("account"));

        /*
        String account = "Mastercard";
        select("acctDropDown_XPATH", account);

        click("submitBtn_XPATH");

        WebElement actualElement = driver.findElement(By.xpath(OR.getProperty("acctHeader_XPATH")));
        String actual = actualElement.getAttribute("innerHTML");

        //can I use hamcrest matcher?
        //Assert.assertEquals(actual, containsString(data.get("account")));

        System.out.println(actual);

        Thread.sleep(2000);

        log.debug("Check Account Activity successfully executed");
        Reporter.log("Check Account Activity successful!");
        */

        }
    }
