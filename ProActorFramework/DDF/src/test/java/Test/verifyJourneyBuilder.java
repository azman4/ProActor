package Test;

import java.awt.Dimension;
import java.awt.Toolkit;
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

public class verifyJourneyBuilder {
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
			String sheetName = "JourneyBuilder";

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

			for (int row = 1; row <= valueSheet.getLastRowNum(); row++) {
				Library.strNewScreenshotFolder = Library.strScreenshotFolder + "//"
						+ valueSheet.getRow(row).getCell(0).getStringCellValue() + "-" + sheetName + formatter.format(date) +" No - "+row;
				baseURL = Library.getParameterFromInputSheet(sheetName, "URL", row);
				
				// Getting data from excel
				
				String question1 = Library.getParameterFromInputSheet(sheetName, "Question 1", row);
				String options1 = Library.getParameterFromInputSheet(sheetName, "Option's List 1", row);
				String optionSelection1 = Library.getParameterFromInputSheet(sheetName, "Option Selection 1", row);
				String question2 = Library.getParameterFromInputSheet(sheetName, "Question 2", row);
				String options2 = Library.getParameterFromInputSheet(sheetName, "Option's List 2", row);
				String optionSelection2 = Library.getParameterFromInputSheet(sheetName, "Option Selection 2", row);
				String question3 = Library.getParameterFromInputSheet(sheetName, "Question 3", row);
				String options3 = Library.getParameterFromInputSheet(sheetName, "Option's List 3", row);
				String optionSelection3 = Library.getParameterFromInputSheet(sheetName, "Option Selection 3", row);
				String solution = Library.getParameterFromInputSheet(sheetName, "Solution", row);
				String coOrdinates = Library.getParameterFromInputSheet(sheetName, "CoOrdinates", row);
				String formSize = Library.getParameterFromInputSheet(sheetName, "Form Size", row);
				String screenSize = Library.getParameterFromInputSheet(sheetName, "Screen Size", row);
				String form = "//form[contains(@id,'form-content')]";
				
				logger.log(Status.INFO, "Data Combination - " + row);
				// SetBrowser Setting
				driver = Library.SetBrowser(Library.strBrowserName);
				
				// Keywords Execution
				driver.manage().deleteAllCookies();
				Thread.sleep(5000);
				Library.LaunchApplication(baseURL, logger);
				Library.verifyScreenSize(screenSize, logger);
				Library.getCoordinates(form, coOrdinates, logger);
				String compType = "Form";
				Library.getSize(form, formSize, compType, logger);
				Library.readFontProperty(logger);
				Library.verifyQuestion(question1, logger);
				Library.verifyOptions(options1, logger);
				Library.selectOption(optionSelection1, logger);
				Library.verifyQuestion(question2, logger);
				Library.verifyOptions(options2, logger);
				Library.selectOption(optionSelection2, logger);
				Library.verifyQuestion(question3, logger);
				Library.verifyOptions(options3, logger);
				Library.selectOption(optionSelection3, logger);
				Library.verifySolution(solution, logger);
				driver.quit();
			}

		} 
		
		catch (Exception exc) {
			System.out.println("=======>" + exc.getMessage());

		}
		extent.flush();
	}
}
