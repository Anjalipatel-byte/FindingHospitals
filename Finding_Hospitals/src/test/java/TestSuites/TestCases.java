package TestSuites;



import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.selenium.Base.*;
import com.selenium.corporateWellness.*;
import com.selenium.diagnosticPage.*;
import com.selenium.identifyingHospitals.*;

public class TestCases extends Base {

	bengaluruHospitals hn = new bengaluruHospitals();
	topCities tc = new topCities();
	formFilling ca = new formFilling();

	@BeforeTest(groups= {"smoke"})
	public void invokeBrowse() throws Exception {
		logger = report.createTest("Executing all the Test Cases");
		invokeBrowser();
		reportPass("Browser is Invoked");
		openURL("websiteURLKey");
	}

	@Test(priority = 1,groups= {"smoke","Regression"})
	public void hospitalNames() throws Exception {
		hn.selectBangalore();
		hn.selectHospital();
		hn.hasParking();
		hn.Operating24X7();
		hn.getHospitalNames();
		reportPass("Hospitals are Retrieved");
	}

	@Test(priority = 2,groups= {"smoke","Regression"})
	public void navigateToHome() throws Exception {
		hn.navigateToHome();
	reportPass("Successfully navigated to Home page from Bangalore Hospitals page");
	}
	@Test(priority = 3,groups= {"smoke","Regression"})
	public void TopCities() throws Exception {
		tc.diagnosticPage();
		tc.list();
	reportPass("Top Cities are retrieved");
	}
	@Test(priority = 4,groups= {"smoke","Regression"})
	public void navigateBack() throws Exception {
		tc.navigateBack();
	reportPass("Successfully navigated to Home page from Diagnostic page");
	}

	@Test(priority = 5,groups= {"smoke","Regression"})
	public void CaptureAlert() throws Exception {
		ca.corporateWellness();
		ca.windowHandles();
		ca.formFill();
		reportPass("Alerts are Obtained");
	}

	@AfterTest
	public void closeBrowser() {
		ca.endReport();
		ca.closeBrowser();
	}

}
