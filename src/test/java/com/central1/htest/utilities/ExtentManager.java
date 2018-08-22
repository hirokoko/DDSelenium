package com.central1.htest.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {

    private static ExtentReports extent;
    private static ExtentHtmlReporter html;

    public static ExtentReports getInstance(){


        if(extent==null){

            //initialize HTMLReporter
            html = new ExtentHtmlReporter(System.getProperty("user.dir") + "//target//extent.html");
            //need to put the report under surefire-reports to see the screenshots when error occurs (//target//surefire-reports//html//extent.html)

            html.config().setTestViewChartLocation(ChartLocation.BOTTOM);
            html.config().setChartVisibilityOnOpen(true);
            html.config().setTheme(Theme.STANDARD);
            html.config().setEncoding("utf-8");

            //initialize ExtentReports and attach the HTMLReporter
            extent = new ExtentReports();
            extent.attachReporter(html);

            //loadConfig method does not exist any more...
           // extent.loadConfig(new File(System.getProperty("ser.dir") + "//src//test//resources//extentconfig//extent-config.xml")); //this method does not exist anymore...
        }

        return extent;
    }

}
