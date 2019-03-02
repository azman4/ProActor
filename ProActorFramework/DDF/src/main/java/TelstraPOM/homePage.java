package TelstraPOM;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import GenericFunctions.Library;

public class homePage {

	public static WebDriver driver;
	public static String strComments = "";
	public static String strURL = "";
	public static String strBrowserName = "";
	public static String strProxy = "";
	static SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YYYY");
	static Date date = new Date();
	static String dateStr = formatter.format(date);
	public static String strScreenshotFolder = "" + dateStr;
	public static String strScreenshotName = "";
	public static String strNewScreenshotFolder = "" + dateStr;
	public static String strLoginWay = "";
	ExtentReports extent;
	static ExtentTest logger;
	public static String strReportsFolder = "" + dateStr;
	
	public static String PersonalTab() {

		return "//a[@href='#tab-personal']";
	}
	public static String BusinessEntTab() {

		return "//a[@href='#tab-personal']/parent::li";
	}
	public static String SmallBusinessTab() {

		return "//a[@href='#tab-personal']/parent::li";
	}
	
	
	public static void selectATab(String tab, ExtentTest logger) throws Exception {
		
		String name = new Object(){}.getClass().getEnclosingMethod().getName();
		try {
			
			WebElement personalTab = driver.findElement(By.xpath(homePage.PersonalTab()));
			Actions action = new Actions(driver);
			action.moveToElement(personalTab).click();
			String value = null;
			
			// Select Personal Tab
			if (tab.equals("Personal"))
			{
			value= driver.findElement(By.xpath(homePage.PersonalTab())).getAttribute("class").trim();
			}
			
			else if (tab.equals("Small Business"))
			{
			driver.findElement(By.xpath(homePage.PersonalTab())).click();
			value= driver.findElement(By.xpath(homePage.PersonalTab())).getAttribute("class").trim();
			}
			
			else if (tab.equals("Business & Enterprise"))
			{
			driver.findElement(By.xpath(homePage.PersonalTab())).click();
			value= driver.findElement(By.xpath(homePage.PersonalTab())).getAttribute("class").trim();
			}

			// Verifying Login Functionality
			if (value.equals("active")) {
				System.out.println(tab+" is selected successfully");
				String temp = Library.getScreenShot(driver,"");
				logger.log(Status.PASS, tab+" is selected successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				
				System.out.println(tab+" is not selected successfully");
				String temp = Library.getScreenShot(driver,"");
				logger.log(Status.PASS, tab+" is not selected successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
				}
			
		} catch (Exception e) {
			System.out.println(tab+" is not selected successfully");
			String temp = Library.getScreenShot(driver,"name");
			logger.log(Status.PASS, tab+" is not selected successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}
	
}
