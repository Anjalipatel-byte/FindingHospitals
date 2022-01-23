package com.selenium.diagnosticPage;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.Base.*;

/* This class is used to navigate to Diagnostic page and get the top cities name */
public class topCities extends Base {

	/* This method is used to click the Diagnostic Page button */
	public void diagnosticPage() throws InterruptedException, Exception {
		logger = report.createTest("Navigating to Diagnostic page");
		Properties prop = new Properties();
		FileInputStream File = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

		prop.load(File);
		By diagnosticPage = By.linkText((prop.getProperty("diagnosticPage_linkTest")));

		findElement(diagnosticPage).click();

		/* To check the page title with the expected one */
		try {
			Assert.assertEquals("Blood Tests | Book Diagnostic Tests from Home at Best Prices | Practo",
					driver.getTitle());
			System.out.println("Now you are in the " + driver.getTitle());
			System.out.println("\t\t\t\tSuccessfully navigated to Diagnostic Page\t\t\t\t");
			Screenshoot("Diagnostic Page");
		}
		/* Incase of mismatch, the below statement is printed */
		catch (AssertionError invalidPage) {
			System.out.println(
					"Invalid Page Loaded or the Page title got changed. Now you are in the page " + driver.getTitle());
		}
	}

	/* This method is used to store and print the top cities name */
	public void list() throws Exception {

		logger = report.createTest("Top cities in Diagnostic page");
		wait = new WebDriverWait(driver, 30);
		Properties prop = new Properties();
		FileInputStream File = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

		prop.load(File);
		try {
			Screenshoot("TopCities");
			By topCities = By.xpath((prop.getProperty("topCities_Xpath")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(topCities));
			List<WebElement> names = driver.findElements(topCities);
			System.out.println("The top cities are:");

			/* To print the stored names using for loop */
			for (int i = 0; i < names.size(); i++) {
				System.out.println(names.get(i).getText());
			}
			System.out.println("\n\t\t\t\tThe top " + names.size() + " cities name has been fetched successfully\n");
			reportPass("All top cities are obtained Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/* This method is used to navigate back to Home page */
	public void navigateBack() {
		logger = report.createTest("Navigating back to Home page from Diagnostic page");
		try {
			driver.navigate().back(); 			// navigating back to the home page
			
			/* To check the page title with the expected one */
			try {
				Assert.assertEquals(
						"Practo | Video Consultation with Doctors, Book Doctor Appointments, Order Medicine, Diagnostic Tests",
						driver.getTitle());
				System.out.println("Now you are in the " + driver.getTitle());
			}
			/* Incase of mismatch, the below statement is printed */
			catch (AssertionError invalidPage) {
				System.out.println("Invalid Page Loaded or the Page title got changed. Now you are in the page "
						+ driver.getTitle());
			}

			reportPass("\t\t\tSuccessfully navigated back to the Home page");
			System.out.println("\n\t\t\tSuccessfully navigated back to Practo Home page\n");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
