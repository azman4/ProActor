package Test;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import GenericFunctions.*;
import TelstraPOM.homePage;

public class buyMobilePhone {
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest logger;

	public static void main(String[] args) throws Exception {

		String baseURL;

		try {
			// Get Data from Config File
			String filePath = System.getenv("filePath");
			FileInputStream fi = new FileInputStream("C:\\ProActor\\ProActorExecutionPanel.xlsm");
			XSSFWorkbook w = new XSSFWorkbook(fi);
			XSSFSheet settingSheet = w.getSheet("ExecuteSettings");
			String sheetName = "BuyMobilePhone";

			Library.strBrowserName = settingSheet.getRow(15).getCell(3).getStringCellValue();
			String strDataFileName = settingSheet.getRow(19).getCell(3).getStringCellValue();
			Library.strScreenshotFolder = settingSheet.getRow(23).getCell(3).getStringCellValue().replaceAll("\\\\","//");
			Library.strReportsFolder = settingSheet.getRow(27).getCell(3).getStringCellValue().replaceAll("\\\\", "//");
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("_dd-MM-YYYY_HH-mm");
			String reportName = Library.strReportsFolder + "//" + sheetName + formatter.format(date) + ".html";
			ExtentHtmlReporter reporter;
			reporter = new ExtentHtmlReporter(reportName);
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			logger = extent.createTest(sheetName);

			// Get Data from Data file
			fi = new FileInputStream(strDataFileName);
			w = new XSSFWorkbook(fi);
			XSSFSheet valueSheet = w.getSheet(sheetName);

			System.out.println(valueSheet.getLastRowNum());

			// SetBrowser Setting
			driver = Library.SetBrowser(Library.strBrowserName);

			for (int row = 1; row <= valueSheet.getLastRowNum(); row++) {
				Library.strNewScreenshotFolder = Library.strScreenshotFolder + "//"
						+ valueSheet.getRow(row).getCell(0).getStringCellValue() + "-" + sheetName + formatter.format(date) +" No - "+row;
				baseURL = Library.getParameterFromInputSheet(sheetName, "URL", row);
				
				// Getting data from excel
				
				String tab = Library.getParameterFromInputSheet(sheetName, "Tab", row);
				String mobile = Library.getParameterFromInputSheet(sheetName, "Mobile", row);
				String colour = Library.getParameterFromInputSheet(sheetName, "Colour", row).toLowerCase().trim();
				String storage = Library.getParameterFromInputSheet(sheetName, "Storage", row);
				String planType = Library.getParameterFromInputSheet(sheetName, "Plan Type", row).trim();
				String plan = Library.getParameterFromInputSheet(sheetName, "Plan", row);
				
				logger.log(Status.INFO, "Data Combination - " + row);

				// Keywords Execution
				Library.LaunchApplication(baseURL, logger);
				Library.selectATab(tab, logger);
				Library.buyMobilePhone(tab, mobile, colour, storage, planType, plan, logger);
			}

		} 
		
		catch (Exception exc) {
			System.out.println("=======>" + exc.getMessage());

		}
		extent.flush();
		driver.quit();
	}
}
