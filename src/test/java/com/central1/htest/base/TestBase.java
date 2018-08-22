package com.central1.htest.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.central1.htest.utilities.ExcelReader;
import com.central1.htest.utilities.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;

    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "//src//test//resources//excel//excel.xlsx");

    public static WebDriverWait wait;


    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    @BeforeSuite
    public void setUp(){

        if(driver==null){

            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//properties//Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fis);
                log.debug("Config file loaded!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//properties//ObjectRepository.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fis);
                log.debug("OR file loaded!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


/* for Jenkins Browser filter configuration
if(System.getenv("browser") != null && !System.getenv("browser").equals("")){
	
	browser = System.getenv("browser");
}else{
	browser = config.getProperty("browser");
}

config.setProperty("browser", browser);
*/
        if(config.getProperty("browser").equals("firefox")){
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//test//resources//executables//geckodriver");
            driver = new FirefoxDriver();
            log.debug("Firefox launched!!!");
        } else if (config.getProperty("browser").equals("chrome")){

            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//test//resources//executables//chromedriver");
            driver = new ChromeDriver();
            log.debug("Chrome launched!!!");

        }


        driver.get(config.getProperty("testsiteurl"));
        log.debug("Navigated to " + config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);

        //explicit wait
        //wait = new WebDriverWait (driver, 1);

    }

    public void click(String locator){

        if(locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        }else if(locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        }else if(locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }

        //Extent Reports
        test.get().info("Clicking on: " + locator);

    }

    public void type(String locator, String value){

        if(locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        }else if(locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        }else if(locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

        //Extent Reports
        test.get().info("Typing in: " + locator + " Entered value as: " + value);

    }

    WebElement dropdown;

    public void select(String locator, String value){

        if(locator.endsWith("_XPATH")) {
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        }else if(locator.endsWith("_CSS")){
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        }else if(locator.endsWith("_ID")){
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);

        //select partial matched text
        WebElement selection  = dropdown.findElement(By.xpath("//option[contains(text(), value)]"));

        String selectedItem = selection.getAttribute("innerHTML");

        select.selectByVisibleText(selection.getAttribute("innerHTML")); //need to pass String...

        //Extent Reports
        test.get().info("Selecting from dropdown: " + locator + " Selected value as: " + selectedItem);

    }


    public boolean isElementPresent(By by){

        try {

            driver.findElement(by);
            return true;


        } catch(NoSuchElementException e){

            return false;
        }
    }

    public static void verifyEquals(String expected, String actual) throws IOException {

        try{

            Assert.assertEquals(actual, expected);

        }catch(Throwable t){

            TestUtil.captureScreenshot();

            //Report NG
            Reporter.log("<br>"+ "Verification failed: " + t.getMessage() + "<br>");
            Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
                    + " height=200 width=200></img></a>");
            Reporter.log("<br>");
            Reporter.log("<br>");

            //Extent Reports
            test.get().fail("Verification failed: " + t.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName).build());



        }
    }


    @AfterSuite
    public void tearDown() {

        if (driver != null) {
            driver.quit();

            log.debug("Test execution completed!");
        }
    }



}
