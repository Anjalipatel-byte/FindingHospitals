package com.selenium.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.selenium.Base.*;

/* This class is used to create Extent HTML report*/
public class HTMLReport {
	public static ExtentReports report;
	/*This method is used to initialize report when its null and return it*/
	public static ExtentReports getReportInstance() {

		if (report == null) {
			String timeStamp = Base.timeStamp();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "/Report/" + timeStamp + ".html");
			report = new ExtentReports();
			report.attachReporter(htmlReporter);

			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Browser", "Chrome/Firefox/Edge");

			htmlReporter.config().setDocumentTitle("UI Automation Results");
			htmlReporter.config().setReportName("Group 2-Finding Hospitals Test Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		}

		return report;
	}

}
