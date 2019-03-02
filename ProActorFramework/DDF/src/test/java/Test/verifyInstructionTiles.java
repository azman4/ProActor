package Test;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import GenericFunctions.*;

public class verifyInstructionTiles {
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
			String sheetName = "InstructionTiles";

			Library.strBrowserName = settingSheet.getRow(15).getCell(3).getStringCellValue();
			String strDataFileName = settingSheet.getRow(19).getCell(3).getStringCellValue();
			Library.strScreenshotFolder = settingSheet.getRow(23).getCell(3).getStringCellValue().replaceAll("\\\\",
					"//");
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
						+ valueSheet.getRow(row).getCell(0).getStringCellValue() + "-" + sheetName
						+ formatter.format(date) + " No - " + row;
				baseURL = Library.getParameterFromInputSheet(sheetName, "URL", row);

				// Getting data from excel

				String compSize = Library.getParameterFromInputSheet(sheetName, "Component Size 1", row);
				String compSize2 = Library.getParameterFromInputSheet(sheetName, "Component Size 2", row);
				String compSize3 = Library.getParameterFromInputSheet(sheetName, "Component Size 3", row);
				String screenSize = Library.getParameterFromInputSheet(sheetName, "Screen Size", row);
				String iconSize = Library.getParameterFromInputSheet(sheetName, "Icon size 1", row);
				String intCompSize = Library.getParameterFromInputSheet(sheetName, "Instruction Tile 1", row);
				String intCompSize2 = Library.getParameterFromInputSheet(sheetName, "Instruction Tile 2", row);
				String intCompSize3 = Library.getParameterFromInputSheet(sheetName, "Instruction Tile 3", row);
				String hLevel = Library.getParameterFromInputSheet(sheetName, "Heading level 1", row);
				String linkURL = Library.getParameterFromInputSheet(sheetName, "Link URL 1", row);

				String compElement = "//div[@class='col-instructional-tile'][1]";
				String iconElement = "//div[@class='col-instructional-tile'][1]//section//div[1]//*[local-name() = 'svg']";
				String linkElement = "//div[@class='col-instructional-tile'][1]//a";
				String intCompElement = "//div[@class='instructionalTiles parbase'][1]";

				String compElement2 = "//div[@class='col-instructional-tile'][3]";
				String iconElement2 = "//div[@class='col-instructional-tile'][3]//section//div[1]//*[local-name() = 'svg']";
				String linkElement2 = "//div[@class='col-instructional-tile'][3]//a";
				String intCompElement2 = "//div[@class='instructionalTiles parbase'][2]";

				String compElement3 = "//div[@class='col-instructional-tile'][4]";
				String iconElement3 = "//div[@class='col-instructional-tile'][4]//section//div[1]//*[local-name() = 'svg']";
				String linkElement3 = "//div[@class='col-instructional-tile'][4]//a";
				String intCompElement3 = "//div[@class='instructionalTiles parbase'][3]";

				logger.log(Status.INFO, "Data Combination - " + row);

				// Keywords Execution
				driver.manage().deleteAllCookies();
				Thread.sleep(5000);
				Library.LaunchApplication(baseURL, logger);
				Library.verifyScreenSize(screenSize, logger);
				logger.log(Status.INFO, "Verifying the Instruction Tiles Component with 2 Steps");
				Library.verifyInstructionTile(compElement, iconElement, intCompElement, compSize, iconSize, intCompSize,
						hLevel, logger);
				Library.verifyLink(linkElement, linkURL, logger);

				logger.log(Status.INFO, "Verifying the Instruction Tiles Component with 3 Steps");
				Library.verifyInstructionTile(compElement2, iconElement2, intCompElement2, compSize2, iconSize,
						intCompSize2, hLevel, logger);
				Library.verifyLink(linkElement2, linkURL, logger);

				logger.log(Status.INFO, "Verifying the Instruction Tiles Component with 4 Steps");
				Library.verifyInstructionTile(compElement3, iconElement3, intCompElement3, compSize3, iconSize,
						intCompSize3, hLevel, logger);
				Library.verifyLink(linkElement3, linkURL, logger);

				driver.quit();
			}

		}

		catch (Exception exc) {
			System.out.println("=======>" + exc.getMessage());

		}
		extent.flush();
	}
}
