package com.selenium.Base;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.extentReport.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

public class Base {

	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverWait wait;
	public ExtentReports report = HTMLReport.getReportInstance();
	public ExtentTest logger;
	JavascriptExecutor js;

	// To call different browsers
	public void invokeBrowser() {
		Scanner input = new java.util.Scanner(System.in);
		System.out.println(
				"\n Enter the corresponding browser number in which the testing need to be executed \n(1/2/3) \n 1.Google Chrome \n 2.Mozilla Firefox \n 3.Microsoft Edge ");
		int browserChoice = input.nextInt(); // Getting input value as int for Browser Choice

		int wrongEntry = 0; // int i is used inside while loop
		int totalChances = 2; // int totalChances is used inside while loop

		/*
		 * while loop is executed when the browser enter invalid choice On repeated
		 * invalid choice the program gets terminated with warning message
		 */
		while (browserChoice >= 4 && wrongEntry < 2) {
			System.out.println("Please Enter the correct choice");
			System.out.println("\n1.Google Chrome \n2.Mozilla Firefox \n3.Microsoft Edge");
			browserChoice = input.nextInt();
			wrongEntry++; // For each wrong entry the value of i increases
			int remainingChances = totalChances - wrongEntry; // int a is used to get the remaining chances

			if (remainingChances == 0 && browserChoice >= 4) // When the condition is satisfied, the program gets
																// terminated with a message
			{
				System.out.println(
						"\n\nSorry, you have reached maximum number of times. Please Enter Valid Browser the next time.");
				System.exit(0);
			} else if (remainingChances >= 1 && browserChoice >= 4) // If the condition is satisfied, a message is
																	// displayed and move to while loop to get input
																	// choice again
			{
				System.out
						.println("**WARNING** \nyou have " + remainingChances + " chance left to enter correct choice");
			}
		}
		switch (browserChoice) {
		case 1:
			driver = setChromeDriver(); // Google Chrome will be opened for case 1.
			break;
		case 2:
			driver = setFirefoxDriver(); // Mozilla Firefox will be opened for case 2.
			break;
		case 3:
			driver = setEdgeDriver(); // Microsoft Edge will be opened for case 3.
			break;
		default:
			break;
		}

		input.close();

		// To maximize the Browser Window
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);

	}

	/*
	 * setChromeDriver() method is used to run the testing in GoogleChrome browser
	 */
	public static WebDriver setChromeDriver() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//drivers//chromedriver.exe");

		/*
		 * ChromeOptions is used to enable the automation, and remove the message
		 * "Chrome is being controlled by an automation software"
		 */

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		driver = new ChromeDriver(options);
		return driver;

	}

	/*
	 * setFirefoxDriver() method is used to run the testing in Mozilla Firefox
	 * browser
	 */
	public static WebDriver setFirefoxDriver() {

		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//drivers//geckodriver.exe");
		driver = new FirefoxDriver();
		return driver;

	}
	/*
	 * setEdgeDriver() method is used to run the testing in Microsoft Edge browser
	 */

	public static WebDriver setEdgeDriver() {

		System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "//drivers//msedgedriver.exe");
		driver = new EdgeDriver();
		return driver;

	}

	// To open the Main Page URL
	public void openURL(String websiteURLKey) throws IOException {
		Properties prop = new Properties();
		try {
			FileInputStream File = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

			prop.load(File);
		} catch (FileNotFoundException locatorError) {
			System.out.println("The properties file couldn't be fetched");
		}

		String Url = prop.getProperty("websiteURLKey");
		driver.get(Url);
		try {
			Assert.assertEquals(
					"Practo | Video Consultation with Doctors, Book Doctor Appointments, Order Medicine, Diagnostic Tests",
					driver.getTitle());
			System.out.println("Now you are in the " + driver.getTitle());
			System.out.println("The browser has been invoked succesfully and the website has been launched");
		}
		/* Incase of mismatch, the below statement is printed */
		catch (AssertionError invalidPage) {
			System.out.println(
					"Invalid Page Loaded or the Page title got changed. Now you are in the page " + driver.getTitle());
		}
		reportPass("URL is Opened");
	}

	/*Draws a red border around the found element. Does not set it back anyhow.*/
	public WebElement findElement(By by) throws Exception {
		WebElement element = driver.findElement(by);
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style','background: ;border: 2px solid red;');", element);
		return element;
	}
	/*To get the TimeStamp*/
		public static String timeStamp(){
			Date date = new Date();
			return date.toString().replaceAll(":", "-").replaceAll(" ", "_");
			
		}

	// Method to show the failed test cases in the report
	public void reportFail(String report) {
		logger.log(Status.FAIL, report);
		takeScreenShotOnFailure();
	}

	// Method to show the passed test cases in the report
	public void reportPass(String report) {
		logger.log(Status.PASS, report);
	}

	// Method to take Screenshot of screen
	public void Screenshoot(String fileName) throws IOException {
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		String timeStamp = timeStamp();
		File destFile = new File(System.getProperty("user.dir") + "/Screenshot/" + timeStamp +" - "+ fileName + ".png");
		Files.copy(srcFile, destFile);
		try {
			FileUtils.copyFile(srcFile, destFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshot/"+timeStamp +" - "+ fileName + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// To take Screenshot when test gets failed
	public void takeScreenShotOnFailure() {

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File src = takeScreenshot.getScreenshotAs(OutputType.FILE);
		String timeStamp = timeStamp();
		File dest = new File(System.getProperty("user.dir") + "/Screenshot/FailedCases/"+ timeStamp+ " - Screenshot.png");
		try {
			FileUtils.copyFile(src, dest);			
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshot/FailedCases/"+timeStamp+" - Screenshot.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// To input all data to the report
	public void endReport() {
		report.flush();
	}

	// To close the Browser
	public void closeBrowser() {
		driver.quit();
	}
	
	

}
