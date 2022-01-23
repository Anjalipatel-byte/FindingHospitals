package com.selenium.corporateWellness;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.selenium.Base.*;
/* The below class is used to fill the form and capture the Alert messages*/
public class formFilling extends Base {

	/* This method is used to select the Corporate Wellness option from the Home page */
	public void corporateWellness() throws Exception {
		logger = report.createTest("Selecting Corporate Wellness option");
		Properties prop = new Properties();
		FileInputStream File = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");
		prop.load(File);
		
		By providersDropDownBox = By.xpath((prop.getProperty("providers_Xpath")));
		By corporateOption = By.xpath((prop.getProperty("corporateWellness_Xpath")));
		try {
			findElement(providersDropDownBox).click();
			findElement(corporateOption).click();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/* This method switches the focus to Coporate Wellness page*/
	public void windowHandles() {
		// Switching to the new tab
		try {
			logger = report.createTest("Switching to Corporate Wellness page");
			Set<String> currentHandle = driver.getWindowHandles();
			Iterator<String> itr = currentHandle.iterator();
			itr.next();
			String corporate = itr.next();
			driver.switchTo().window(corporate);
			
			/* Here, the page title is compared with the expected page title*/
			try {
				Assert.assertEquals("Employee Health | Corporate Health & Wellness Plans | Practo", driver.getTitle());
				System.out.println("Now you are in the " +driver.getTitle());
				System.out.println("\t\t\t\tSuccessfully navigated to Corporate Wellness Page\t\t\t");
				Screenshoot("Corporate Wellness");	//To take Screenshot of the page
				reportPass("Successfully navigated to Corporate Wellness page");
			}
			
			/* Incase of mismatch, the below statement is printed */
			catch (AssertionError invalidPage)
			{
				reportFail(invalidPage.getMessage());
				System.out.println("Invalid Page Loaded or the Page title got changed. Now you are in the page "+driver.getTitle());
			}
			} catch (Exception e) {
			}
}
		/* The below method is used to fill the form and capture the alert messages*/
		public void formFill() throws InterruptedException, IOException {

		logger = report.createTest("Filling invalid details and capturing the alert");
		Properties prop = new Properties();
		FileInputStream File = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\selenium\\config\\Config.properties");
		prop.load(File);
		
		/* Storing the Xpath in By element*/ 
		By nameTextBox = By.xpath(prop.getProperty("nameTextBox_Xpath"));
		By organizationNameTextBox = By.id(prop.getProperty("organizationName_Id"));
		By emailIdTextBox = By.id(prop.getProperty("email_Id"));
		By contactNumberTextBox = By.id(prop.getProperty("contact_Id"));
		By organizationSize=By.xpath(prop.getProperty("organizationSizeDropDownBox_Xpath"));
		By button = By.id(prop.getProperty("ScheduleADemoButton_Id"));
		
		/* Reading the Excel Sheet*/
		String path=System.getProperty("user.dir") + "//src//test//resources//InputData.xlsx";
		File file = new File(path);
		FileInputStream fs = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		WebDriverWait wait = new WebDriverWait(driver,180);
		
		/* To fill the form, submit it and get the Alerts */
		try {
			/* Storing as WebElement*/
			WebElement nameBox, organizationNameBox, emailIdBox, contactBox, organizationSizeDropDown,submitButton;
			nameBox = driver.findElement(nameTextBox);
			organizationNameBox = driver.findElement(organizationNameTextBox);
			emailIdBox = driver.findElement(emailIdTextBox);
			contactBox = driver.findElement(contactNumberTextBox);
			organizationSizeDropDown = driver.findElement(organizationSize);
			submitButton = driver.findElement(button);
			Select select = new Select(organizationSizeDropDown);
			
			/*Storing the data which is driven from excel sheet as String type */
			String shortNumber, longNumber, Number;
			shortNumber = formatter.formatCellValue(sheet.getRow(1).getCell(3));
			longNumber = formatter.formatCellValue(sheet.getRow(2).getCell(3));
			Number = formatter.formatCellValue(sheet.getRow(3).getCell(3));
			
			String name, organizationName, eMailId, invalidEMailId;
			name = sheet.getRow(1).getCell(0).getStringCellValue();					
			organizationName = sheet.getRow(1).getCell(1).getStringCellValue();
			eMailId = sheet.getRow(2).getCell(2).getStringCellValue();
			invalidEMailId = sheet.getRow(1).getCell(2).getStringCellValue();
			
			/*TestCase 1: Filling all the fields except 'Organization size'  */
			try {
			System.out.println("\n\t\t\t\t ****Test Case_1**** \t\t\t\t");
			nameBox.sendKeys(name);
			organizationNameBox.sendKeys(organizationName);
			emailIdBox.sendKeys(eMailId);
			contactBox.sendKeys(Number);
			submitButton.click();
			
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully ****");
			reportPass("Alert message has been captured successfully for Test Case One");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert message couldn't be located, as the captcha dialog page maybe popped up.");
			}	
			
			/* TestCase 2: Filling all the fields except 'Name'*/
			try {
			System.out.println("\n\t\t\t\t ****Test Case_2**** \t\t\t\t");
			nameBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			select.selectByIndex(1);
			submitButton.click();
			
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Two");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			/*TestCase 3: Filling all the fields except 'Organization Name'*/
			try {
			System.out.println("\t\t\t\t ****Test Case_3**** \t\t\t\t");
			organizationNameBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			nameBox.sendKeys(name);
			select.selectByIndex(2);
			submitButton.click();
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Three");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			/*TestCase 4: Filling all the fields except 'Email ID'*/
			try {
			System.out.println("\t\t\t\t ****Test Case_4**** \t\t\t\t");
			emailIdBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			select.selectByIndex(3);
			organizationNameBox.sendKeys(organizationName);
			submitButton.click();
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Four");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			/*TestCase 5: Filling all the fields with invalid data in 'Email ID' field*/
			try {
			System.out.println("\t\t\t\t ****Test Case_5**** \t\t\t\t");
			emailIdBox.sendKeys(invalidEMailId);
			select.selectByIndex(4);
			submitButton.click();
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Five");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			/*TestCase 6: Filling all the fields except 'Contact Number'*/
			try {
			System.out.println("\t\t\t\t ****Test Case_6**** \t\t\t\t");
			emailIdBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			emailIdBox.sendKeys(eMailId);
			contactBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			select.selectByIndex(5);
			submitButton.click();
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Six");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			/*TestCase 7: Filling all the fields with invalid data in 'Contact Number' field'*/
			try {
			System.out.println("\t\t\t\t ****Test Case_7**** \t\t\t\t");
			contactBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			contactBox.sendKeys(shortNumber);
			submitButton.click();
			
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Seven");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			/*TestCase 8: Filling all the fields with invalid data in 'Contact Number' field'*/
			try {	
			System.out.println("\t\t\t\t ****Test Case_8**** \t\t\t\t");
			contactBox.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			contactBox.sendKeys(longNumber);
			submitButton.click();
		
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			System.out.println("Alert: " + alert.getText());
			alert.accept();
			System.out.println(" ****The alert has been captured successfully **** ");
			reportPass("Alert message has been captured successfully for Test Case Eight");
			}catch(Exception e)
			{
				reportFail(e.getMessage());
				System.out.println("The alert couldn't be located, as the captcha dialog page maybe popped up.");
			}
			
			
			workbook.close(); //Closing the workbook as to avoid leakage
		reportPass("Successfully driven all the input data from Excel sheet and all the test cases have been executed");
		} catch(Exception e)
		{
			reportFail(e.getMessage());
		}
	}
}

 