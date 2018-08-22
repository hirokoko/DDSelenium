package com.central1.htest.utilities;

import com.central1.htest.base.TestBase;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

public class TestUtil extends TestBase {

    public static String screenshotPath;
    public static String screenshotName;

    public static void captureScreenshot() throws IOException {

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        Date d = new Date();
        screenshotName = d.toString().replace(":", "_").replace(" ", "_")+ ".jpg";
        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//target//surefire-reports//html//" + screenshotName));
    }

    @DataProvider(name="dp")
    public Object[][] getData(Method m){

        String sheetName = m.getName();

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows-1][1];

        //create a hash table for each row to avoid passing all the columns into individual test cases
        Hashtable<String, String> table = null;


        for(int rowNum = 2; rowNum <= rows; rowNum++){

            table = new Hashtable<String, String>();

            for (int colNum = 0; colNum < cols; colNum++){

                // data[0][0]
                //data[rowNum - 2] [colNum] = excel.getCellData(sheetName, colNum, rowNum);
                //put takes (key, value) and key is the first row of the Excel sheet
                table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
                //0 row and 0 column should be the first table and there is only one column
                data[rowNum - 2] [0] = table;

            }
        }

        return data;
    }

    public static boolean isTestRunnable(String testName, ExcelReader excel){

        String sheetName = "test_suite";
        int rows = excel.getRowCount(sheetName);

        for(int rNum=2; rNum<=rows; rNum++){

            String testcase = excel.getCellData(sheetName, "TCID", rNum);

            if(testcase.equalsIgnoreCase(testName)){
                String runmode = excel.getCellData(sheetName, "Runmode", rNum);

                if(runmode.equalsIgnoreCase("Y")){
                    return true;
                } else {
                    return false;
                }

            }

        }

        return false;

    }

}
