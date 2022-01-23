package com.selenium.identifyingHospitals;

import org.testng.Assert;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.Base.Base;

/* This class is used to navigate to Bangalore Hospitals page and get the hospital names which satisfy the given condition and navigate back to Home page*/
public class bengaluruHospitals extends Base {

	/* This method is used to select the Bangalore location */
	public void selectBangalore() throws InterruptedException, Exception {

		Properties prop = new Properties();
		FileInputStream File = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

		prop.load(File);
		logger = report.createTest("Select Bangalore location");

		By Location = By.xpath(prop.getProperty("search_Xpath"));
		By Banglore = By.xpath(prop.getProperty("bangalore_Xpath"));
		By searchInBangalore = By.xpath(prop.getProperty("searchInBangalore_Xpath"));
		
		try {
			wait = new WebDriverWait(driver, 30);
			WebElement location = findElement(Location);
			location.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			location.sendKeys("Bangalore");
			try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(Banglore));
			findElement(Banglore).click();
			}catch(NoSuchElementException e)
			{
				findElement(searchInBangalore).click();
			}
			reportPass("Bangalore is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	/* Below method is used to select Hospital dropDownBox*/
	public void selectHospital() throws IOException {
		Properties prop = new Properties();
		FileInputStream File = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

		prop.load(File);
		logger = report.createTest("Select Hospital in search box");
		By SearchBox = By.xpath((prop.getProperty("searchBox_Xpath")));
		By Hospital = By.xpath((prop.getProperty("hospital_Xpath")));
		try {
			findElement(SearchBox).sendKeys("Hospital");
			wait.until(ExpectedConditions.visibilityOfElementLocated(Hospital));
			findElement(Hospital).click();

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		/* The actual page title is compared with the expected page title*/
		try {
			Assert.assertEquals("Best Hospitals in Bangalore - Book Appointment Online, View Fees, Reviews | Practo",
					driver.getTitle());
			System.out.println("Now you are in the " + driver.getTitle());
			System.out.println("Successfully navigated to page of Bangalore hospitals");
			Screenshoot("bangaloreHospitals");

		}
		/* Incase of mismatch, the below statement is printed */
		catch (AssertionError invalidPage) {
			System.out.println(
					"Invalid Page Loaded or the Page title got changed. Now you are in the page " + driver.getTitle());
		}
	}
	/* To select the Has Parking checkbox*/	
	public void hasParking() {
			logger = report.createTest("Select Parking checkBox");
		
		try {
			Properties prop = new Properties();
			FileInputStream File = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

			prop.load(File);
			
			By Filters = By.xpath((prop.getProperty("allFilters_Xpath")));
			By Parking = By.xpath((prop.getProperty("hasParking_Xpath")));
			wait.until(ExpectedConditions.elementToBeClickable(Filters));
			findElement(Filters).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Parking));
			findElement(Parking).click();
			
			reportPass("Has Parking is selected Successfully");
			} catch (Exception e) {
			reportFail(e.getMessage());
			}
		}
	
	/* This method is used to select the 24X7 checkBox*/
		public void Operating24X7() {
			logger = report.createTest("Select 24X7 checkBox");
		try {
			Properties prop = new Properties();
			FileInputStream File = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

			prop.load(File);
			
			By Operating24X7 = By.xpath((prop.getProperty("24X7_Xpath")));
			WebElement Functioning24X7 = wait.until(ExpectedConditions.visibilityOfElementLocated(Operating24X7));
			wait.until(ExpectedConditions.stalenessOf(Functioning24X7));
			findElement(Operating24X7).click();
			//Thread.sleep(3000);
			TimeUnit.SECONDS.sleep(3);
			reportPass("Open 24X7 is selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
		/* This method is used to fetch the Hospital Names which satisfy the given condition*/
		public void getHospitalNames() throws Exception {
			Properties prop = new Properties();
			FileInputStream File = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");

			prop.load(File);
			logger = report.createTest("Hospitals in Bangalore with more than 3.5 ratings");
			By ratings = By.xpath((prop.getProperty("ratings_Xpath")));
			By hospitalNames = By.xpath((prop.getProperty("hospitalNames_Xpath")));
			By nextButton = By.xpath((prop.getProperty("nextButton_Xpath")));
			By totalHospitals = By.xpath((prop.getProperty("noOfHospitals_Xpath")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(totalHospitals));
			String TotalHospitals = driver.findElement(totalHospitals).getText();		//Getting the value of number of hospitals
			int numOfHospitals = Integer.parseInt(TotalHospitals);						//Converting it into integer Data Type
			System.out.println("Totally "+numOfHospitals+ " hospitals are functioning 24x7 which has parking facility");

			int totalPages;		
			int remainder = numOfHospitals % 10;			//Since a page can hold 10 Hospital data, getting the remainder 
			int quotient = numOfHospitals / 10;				//
			
			if (remainder == 0) {
				totalPages = quotient;
			} else {
				totalPages = quotient + 1;
			}
			int serialNumber=0;				//Used to print the serialNumber for Hospital Names
			
			/* Storing and printing the Hospital Names*/
			try {
			for (int p = 1; p <= totalPages; p++) {
				System.out.println(driver.getTitle());
				List<WebElement> Ratings = driver.findElements(ratings);
				List<WebElement> HospitalNames = driver.findElements(hospitalNames);
				int noOfHospitalsInAPage = 0;
				for (int i = 0; i < Ratings.size(); i++) {
					float rate = Float.parseFloat(Ratings.get(i).getText());
					if (rate > 3.5) {
						serialNumber++;
						System.out.println(serialNumber+"-"+Ratings.get(i).getText() + " - " + HospitalNames.get(i).getText());
						noOfHospitalsInAPage++;
					}
				
				}
				/* If no Hospitals in a page has ratings more than 3.5 then the if statement is executed*/
				if(noOfHospitalsInAPage==0)
				{
					System.out.println("No hospitals in this page with a rating of above 3.5");
				}
				/* Clicking the next button to iterate to next page still reaching the last page*/
				if(p<totalPages) {
					wait.until(ExpectedConditions.elementToBeClickable(nextButton));
					findElement(nextButton).click();
				}
				/*Else part is printed when the last page is reached */
				else {
					System.out.println("All the hospital names with ratings more than 3.5 are displayed and the data has been fetched successfully");
				}
			}

			reportPass("All Hospital are obtained Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
		/* This method is used to navigate back to home by clicking the Practo logo in the top of the page*/
		public void navigateToHome() {
			logger = report.createTest("Navigating back to the homePage");
			/* To click the Practo logo*/
			try {
		
			Properties prop = new Properties();
			FileInputStream File = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");
			prop.load(File);
			By homePage = By.xpath((prop.getProperty("homePage_Xpath")));
			wait.until(ExpectedConditions.elementToBeClickable(homePage));
		    driver.findElement(homePage).click();
			
		    /* To check the Page tilte with the expected one*/
			try {
			Assert.assertEquals("Practo | Video Consultation with Doctors, Book Doctor Appointments, Order Medicine, Diagnostic Tests", driver.getTitle());
			System.out.println("Now you are in the " +driver.getTitle());
			System.out.println("\n\t\t\t Successfully navigated to Home Page");
		}
		/* Incase of mismatch, the below statement is printed */
		catch (AssertionError invalidPage)
		{
			System.out.println("Invalid Page Loaded or the Page title got changed. Now you are in the page "+driver.getTitle());
		}
		Screenshoot("homePage");
		reportPass("Successfully navigated back to the Home page");
		}catch(Exception e)
		{
			reportFail(e.getMessage());
		}
		
	}

}
