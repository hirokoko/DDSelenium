package com.central1.htest.testcases;

import com.central1.htest.base.TestBase;
import com.central1.htest.utilities.TestUtil;
import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class LoginTest extends TestBase {

    @Test (dataProviderClass= TestUtil.class, dataProvider="dp")
    public void loginTest(Hashtable<String, String> data) throws InterruptedException {

        //took off the param String acctNum, String pac from the signature until I figure out how to read large int from Excel

        log.debug("Inside Login Test");

        driver.findElement(By.xpath(OR.getProperty("acctNumInput_XPATH"))).clear();
        //driver.findElement(By.xpath(OR.getProperty("acctNumInput_XPATH"))).sendKeys("33030757");
        type("acctNumInput_XPATH", data.get("acctNum").substring(1));


        //driver.findElement(By.xpath(OR.getProperty("pacInput_XPATH"))).sendKeys("Blue1234");
        type("pacInput_XPATH", data.get("pac"));


        //select input field entry for Blueshore acctnumber and replace it with the whole number
        //driver.findElement(By.xpath(OR.getProperty("acctNumInput_XPATH"))).sendKeys(Keys.chord(Keys.COMMAND, "a"), acctNum);
        //driver.findElement(By.xpath(OR.getProperty("acctNumInput_XPATH"))).sendKeys(acctNum);
        //driver.findElement(By.xpath(OR.getProperty("pacInput_XPATH"))).sendKeys(pac);


        //driver.findElement(By.xpath(OR.getProperty("loginBtn_XPATH"))).click();
        click("loginBtn_XPATH");

        //Thread.sleep(3000);


        Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("logoutLink_XPATH"))), "Login not successful.");

        log.debug("Login successfully executed");
        Reporter.log("Login successful!");
    }


}
