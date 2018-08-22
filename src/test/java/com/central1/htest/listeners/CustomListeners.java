package com.central1.htest.listeners;

import com.aventstack.extentreports.*;
import com.central1.htest.base.TestBase;
import com.central1.htest.utilities.ExtentManager;
import com.central1.htest.utilities.TestUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.IOException;

public class CustomListeners extends TestBase implements ITestListener {

    public ExtentReports rep = ExtentManager.getInstance();

    public void onTestStart(ITestResult iTestResult) {

        //create test for ExtentReports
        ExtentTest extentTest = rep.createTest(iTestResult.getMethod().getMethodName().toUpperCase());
        test.set(extentTest);

    }

    public void onTestSuccess(ITestResult iTestResult) {

        Reporter.log("Success in testcase " + iTestResult);
        //log for ExtentReports
        //test.log(Status.PASS, iTestResult.getName().toUpperCase() + "PASS");

        //Extent Reports
        test.get().pass("PASS");

        //endTest is no longer accepted
        //rep.endTest(test);
        rep.flush();
    }

    public void onTestFailure(ITestResult iTestResult) {

        System.setProperty("org.uncommons.reportng.escape-output", "false");

        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reporter.log("Click to see screenshot.");
        Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200</img></a>");


        //logging fail log events for ExtentReports
        try {
            test.get().fail( iTestResult.getName().toUpperCase() + "Failed with exception: " + iTestResult.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName).build() );
        } catch (IOException e) {
            e.printStackTrace();
        }


        //  test.log(Status.FAIL, test.addScreenCaptureFromPath(TestUtil.screenshotName));

        //endTest is no longer accepted
        //rep.endTest(test);
        rep.flush();
    }

    public void onTestSkipped(ITestResult iTestResult) {

        //Extent Reports
        test.get().skip(iTestResult.getThrowable());
        rep.flush();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

        //Extent Reports
        rep.flush();
    }
}
