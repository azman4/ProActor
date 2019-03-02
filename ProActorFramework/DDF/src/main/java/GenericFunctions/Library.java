package GenericFunctions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import TelstraPOM.homePage;

//import com.thoughtworks.selenium.Wait.WaitTimedOutException;

public class Library {
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
	private static final Object LOCK = new Object();

	public static synchronized String getParameterFromInputSheet(String sheetName, String parameter, int rowNum) {
		String value = null;
		String filePath = System.getenv("filePath");
		try {
			FileInputStream fi = new FileInputStream("C:\\ProActor\\ProActorExecutionPanel.xlsm");
			XSSFWorkbook w = new XSSFWorkbook(fi);
			XSSFSheet settingSheet = w.getSheet("ExecuteSettings");
			String strDataFileName = settingSheet.getRow(19).getCell(3).getStringCellValue();
			FileInputStream file = new FileInputStream(new File(strDataFileName));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int paramCol = -1;
			Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				try {
					if (cell.getStringCellValue().equals(parameter)) {
						paramCol = cell.getColumnIndex();
					}
				} catch (Exception e) {
				}
			}
			try {
				value = sheet.getRow(rowNum).getCell(paramCol).getStringCellValue();
			} catch (Exception e) {
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Please verify the Data sheet, and the path where it is saved are correct");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void SetParameterFromInputSheet(String sheetName, String parameter, int rowNum, String Value) {
		synchronized (LOCK) {
			try {
				String filePath = System.getenv("filePath");
				FileInputStream fi = new FileInputStream(filePath);
				XSSFWorkbook w = new XSSFWorkbook(fi);
				XSSFSheet settingSheet = w.getSheet("ExecuteSettings");
				String strDataFileName = settingSheet.getRow(19).getCell(3).getStringCellValue();
				FileInputStream file = new FileInputStream(new File(strDataFileName));

				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheet(sheetName);

				int paramCol = -1;
				Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					try {
						if (cell.getStringCellValue().equals(parameter)) {
							paramCol = cell.getColumnIndex();
						}
					} catch (Exception e) {
					}
				}
				try {
					XSSFRow row1 = sheet.getRow(rowNum);
					XSSFCell cellA1 = row1.createCell(paramCol);
					cellA1.setCellValue(Value);
				} catch (Exception e) {
				}

				FileOutputStream out = new FileOutputStream(new File(filePath));

				workbook.write(out);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Please verify the Data sheet, and the path where it is saved are correct");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static WebDriver SetBrowser(String strBrowserName) {

		strBrowserName = strBrowserName.toUpperCase();
		if (strBrowserName.equals("IE")) {
			// Change the path to E: drive if D not present
			System.setProperty("webdriver.ie.driver", "E:/Jars/IEDriverServer.exe");
			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.DIRECT);
			DesiredCapabilities ieCapabilities = new DesiredCapabilities();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability(CapabilityType.VERSION, "10");
			ieCapabilities.setCapability(CapabilityType.PROXY, proxy);
			// driver = new InternetExplorerDriver(ieCapabilities);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (strBrowserName.equals("GOOGLE CHROME")) {
			// Change the path to E: drive if D not present
			System.setProperty("webdriver.chrome.driver", "C:/Jars/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=C:/Users/azansari/AppData/Local/Chrome/User Data/Default");
			options.addArguments("--start-maximized");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} else {
			System.setProperty("webdriver.gecko.driver", "C:\\Jars\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		return driver;
	}

	public static void CaptureScreenShot(String strScreenshotName) {
		try {
			try {
				driver.manage().window().maximize();
			} catch (Exception e) {
			}
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy
			// somewhere

			FileUtils.copyFile(scrFile, new File(strScreenshotName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getScreenShot(WebDriver driver, String name) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String path = strNewScreenshotFolder + "\\" + name + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed" + e.getMessage());
		}
		return path;
	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean checkElementInsideElement(By parentBy, By childBy) {
		try {
			driver.findElement(parentBy).findElement(childBy);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean popup_isAlertPresent(int TimeOutinSeconds) {
		for (int i = 0; i < TimeOutinSeconds; i++) {
			try {
				Thread.sleep(500);
				Alert alt = driver.switchTo().alert();
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	private static boolean IsAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (Exception e) {
			return false;
		} // catch
	}

	public static void waitForPageLoaded(int TimeOutinSeconds) throws Exception {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, TimeOutinSeconds);
		try {
			wait.until(expectation);
		} catch (Exception e) {
			throw e;
		}
	}

	private static ExpectedCondition<Boolean> WaitForAjax(final long timeout) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				final long startTime = System.currentTimeMillis();
				final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

				while ((startTime + timeout) >= System.currentTimeMillis()) {
					final Boolean scriptResult = (Boolean) javascriptExecutor
							.executeScript("return jQuery.active == 0");
					if (scriptResult)
						return true;
					delay(100);
				}
				return false;
			}
		};
	}

	private static void delay(final long amount) {
		try {
			Thread.sleep(amount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void clickWebElement(WebElement weElement, WebDriver wdDriver) {

		// Scroll the browser to the element's Y position
		((JavascriptExecutor) wdDriver).executeScript("window.scrollTo(0," + weElement.getLocation().y + ")");
		// Click the element
		int iAttempts = 0;
		while (iAttempts < 5) {
			try {
				weElement.click();
				waitForPageLoaded(20);
				break;
			} catch (Exception e) {
			}
			iAttempts++;
		}
	}

	public static void getCoordinates(String element, String coOrdinates, ExtentTest logger) throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// Locate element for which you wants to retrieve x y coordinates.
		WebElement elementV = driver.findElement(By.xpath(element));
		WebElement elementC = wait.until(ExpectedConditions.visibilityOf(elementV));

		// Used points class to get x and y coordinates of element.

		Point classname = elementC.getLocation();

		int xcordi = classname.getX();
		System.out.println("X coordinate of Element(Left) " + xcordi + " pixels.");
		int ycordi = classname.getY();
		System.out.println("Y coordinate of Element(Top) " + ycordi + " pixels.");

		String[] coOrdinatesValue = coOrdinates.split(",");
		String ExpectedXcoordinate = coOrdinatesValue[0];
		int ExpectedXcordi = Integer.parseInt(ExpectedXcoordinate);
		String ExpectedYcoordinate = coOrdinatesValue[1];
		int ExpectedYcordi = Integer.parseInt(ExpectedYcoordinate);

		if (xcordi == ExpectedXcordi) {

			System.out.println(
					"Actual & Expected values of X coordinates for Component have matched, value is " + xcordi);
			String temp = Library.getScreenShot(driver,
					"Actual & Expected values of X coordinates for Component have matched, value is " + xcordi);
			logger.log(Status.PASS,
					"Actual & Expected values of X coordinates for Component have matched, value is " + xcordi,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			System.out
					.println("Actual & Expected values of X coordinates for Component dont match, value is " + xcordi);
			String temp = Library.getScreenShot(driver,
					"Actual & Expected values of X coordinates for Component dont match, value is " + xcordi);
			logger.log(Status.FAIL,
					"Actual & Expected values of X coordinates for Component dont match, value is " + xcordi,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

		if (ycordi == ExpectedYcordi) {

			System.out.println(
					"Actual & Expected values of Y coordinates for Component have matched, value is " + ycordi);
			String temp = Library.getScreenShot(driver,
					"Actual & Expected values of Y coordinates for Component have matched, value is " + ycordi);
			logger.log(Status.PASS,
					"Actual & Expected values of Y coordinates for Component have matched, value is " + ycordi,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			System.out
					.println("Actual & Expected values of Y coordinates for Component dont match, value is " + ycordi);
			String temp = Library.getScreenShot(driver,
					"Actual & Expected values of Y coordinates for Component dont match, value is " + ycordi);
			logger.log(Status.FAIL,
					"Actual & Expected values of Y coordinates for Component dont match, value is " + ycordi,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void readFontProperty(ExtentTest logger) throws Exception {

		// Locate text string element to read It's font properties.
		WebElement text = driver.findElement(By.xpath("//legend[text()='What is your specialization in?']"));

		// Read font-size property and print It In console.
		String fontSize = text.getCssValue("font-size");
		System.out.println("Font Size -> " + fontSize);

		// Read color property and print It In console.
		String color = text.getCssValue("color");
		System.out.println("Font Color -> " + color);

		String s1 = color.substring(4);
		color = s1.replace(')', ' ');
		StringTokenizer st = new StringTokenizer(color);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());
		Color c = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(c.getRGB()).substring(2);
		System.out.println(hex);

		// Read font-family property and print It In console.
		String fontFamily = text.getCssValue("font-family");
		System.out.println("Font Family -> " + fontFamily);

		// Read text-align property and print It In console.
		String fonttxtAlign = text.getCssValue("text-align");
		System.out.println("Font Text Alignment -> " + fonttxtAlign);
		logger.log(Status.INFO, "Font Size is -> " + fontSize + "\n" + ", Font Color Hex code is -> " + hex + "\n"
				+ "& Font Family is -> " + fontFamily);
	}

	public static void getSize(String element, String compSize, String compType, ExtentTest logger) throws Exception {
		// Locate element for which you wants to get height and width.
		WebElement elementV = driver.findElement(By.xpath(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementV);
		Thread.sleep(500);
		
		// Get width of element.
		int ImageWidth = elementV.getSize().getWidth();
		System.out.println("Image width Is " + ImageWidth + " pixels");

		// Get height of element.
		int ImageHeight = elementV.getSize().getHeight();

		System.out.println("Image height Is " + ImageHeight + " pixels");
		String[] coOrdinatesValue = compSize.split("x");
		String ExpectedXcoordinate = coOrdinatesValue[0];
		int ExpectedXcordi = Integer.parseInt(ExpectedXcoordinate);
		String ExpectedYcoordinate = coOrdinatesValue[1];
		int ExpectedYcordi = Integer.parseInt(ExpectedYcoordinate);

		if (ImageWidth == ExpectedXcordi && ImageHeight == ExpectedYcordi) {

			System.out.println("Actual & Expected values of " + compType + " size have matched, value is " + ImageWidth
					+ "x" + ImageHeight);
			String temp = Library.getScreenShot(driver, "Actual & Expected values of " + compType
					+ " size have matched, value is " + ImageWidth + "x" + ImageHeight);
			logger.log(
					Status.PASS, "Actual & Expected values of " + compType + " size have matched, value is "
							+ ImageWidth + "x" + ImageHeight,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

		} else {
			System.out.println("Actual & Expected values of " + compType + " size dont match, value is " + ImageWidth
					+ "x" + ImageHeight);
			String temp = Library.getScreenShot(driver, "Actual & Expected values of " + compType
					+ " size dont match, value is " + ImageWidth + "x" + ImageHeight);
			logger.log(Status.FAIL, "Actual & Expected values of " + compType + " size dont match, value is "
					+ ImageWidth + "x" + ImageHeight, MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public static void verifyScreenSize(String screenSize, ExtentTest logger) throws Exception {

		// Locate element for which you wants to get height and width.
		Dimension screenSizeActual = Toolkit.getDefaultToolkit().getScreenSize();
		// the screen height
		screenSizeActual.getHeight();
		// the screen width
		screenSizeActual.getWidth();
		int screenHeight = screenSizeActual.height;
		int screenWidth = screenSizeActual.width;

		System.out.println(screenHeight);
		System.out.println(screenWidth);

		String[] coOrdinatesValue = screenSize.split(",");
		String ExpectedXcoordinate = coOrdinatesValue[0];
		int ExpectedXcordi = Integer.parseInt(ExpectedXcoordinate);
		String ExpectedYcoordinate = coOrdinatesValue[1];
		int ExpectedYcordi = Integer.parseInt(ExpectedYcoordinate);

		if (screenWidth == ExpectedXcordi && screenHeight == ExpectedYcordi) {

			System.out.println("Actual & Expected values of screen size have matched, value is " + screenWidth + ","
					+ screenHeight);
			String temp = Library.getScreenShot(driver,
					"Actual & Expected values of screen size have matched, value is " + screenWidth + ","
							+ screenHeight);
			logger.log(Status.PASS, "Actual & Expected values of screen size have matched, value is " + screenWidth
					+ "," + screenHeight, MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			System.out.println(
					"Actual & Expected values of screen size dont match, value is " + screenWidth + "," + screenHeight);
			String temp = Library.getScreenShot(driver,
					"Actual & Expected values of screen size dont match, value is " + screenWidth + "," + screenHeight);
			logger.log(Status.INFO,
					"Actual & Expected values of screen size dont match, value is " + screenWidth + "," + screenHeight,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}
	// <<<<<<<<<<<<<<<<<<<<<<<Telstra - Functions (Please only add new Functions
	// after this )>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public static void LaunchApplication(String baseURL, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		driver.get(baseURL);
		driver.manage().window().maximize();
		waitForPageLoaded(10);
		Thread.sleep(3000);
		// Verifying Login Functionality
		if (Library.isElementPresent(By.xpath("//span[@class='site-logo']//parent::a"))) {
			System.out.println("Main page is opened succesfully");
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "Application is Launched",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "Application is NOT Launched",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void selectATab(String tab, ExtentTest logger) throws Exception {

		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		try {

			driver.findElement(By.xpath("//a[text()='" + tab + "']")).click();

			// Select Personal Tab
			if (tab.equals("Personal") && Library.isElementPresent(By.xpath("//h2[@class='panel-heading  theme']"))) {
				System.out.println(tab + " is selected successfully");
				String temp = Library.getScreenShot(driver, "");
				logger.log(Status.PASS, tab + " is selected successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}

			// Select Small Business Tab
			else if (tab.equals("Small Business")
					&& Library.isElementPresent(By.xpath("//h2[@class='sub-heading text-center']"))) {
				System.out.println(tab + " is selected successfully");
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, tab + " is selected successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}

			// Select Business & Enterprise Tab
			else if (tab.equals("Business & Enterprise")
					&& Library.isElementPresent(By.xpath("//span[@class='sub-heading theme']"))) {
				System.out.println(tab + " is selected successfully");
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, tab + " is selected successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}

			else {

				System.out.println(tab + " is not selected successfully");
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.FAIL, tab + " is not selected successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}

		} catch (Exception e) {
			System.out.println(tab + " is not selected successfully");
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, tab + " is not selected successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	// Buy a Mobile phone from Personal & Small Business tabs

	public static void buyMobilePhone(String tab, String mobile, String colour, String storage, String planType,
			String plan, ExtentTest logger) throws Exception {

		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);

			// Select Personal Tab
			if (tab.equals("Personal")) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//a[text()='Explore Mobiles on a Plan']"))).click();
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[text()='" + mobile + "']/ancestor::a"))).click();
				System.out.println(mobile + " product detail page is displayed successfully");
				String temp = Library.getScreenShot(driver, mobile + " product detail page");
				logger.log(Status.PASS, mobile + " product detail page is displayed successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath("//*[@id='keyFeatures']/following-sibling::div//button[@data-colour='" + colour + "']")))
						.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//input[@value='" + storage + "' and @name='storageSize']/parent::label"))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//input[@value='" + planType + "' and @name='productDetailPlans']/parent::label")))
						.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='" + plan + "']")))
						.click();
				String temp1 = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, plan + " plan is under " + planType + " is selected",
						MediaEntityBuilder.createScreenCaptureFromPath(temp1).build());
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//h3[text()='" + plan + "']//following::a[text()='Select plan']"))).click();

				if (Library.isElementPresent(By.id("btnNext-acc-whatYouNeed"))) {
					System.out.println(mobile + " is added to cart successfully");
					String temp2 = Library.getScreenShot(driver, plan + " plan, under " + planType + " is selected");
					logger.log(Status.PASS, mobile + " is added to cart successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(temp2).build());
				} else {
					System.out.println(mobile + " is not added to cart successfully");
					String temp3 = Library.getScreenShot(driver, mobile + " is not added to cart");
					logger.log(Status.FAIL, mobile + " is not added to cart successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(temp3).build());
				}
			}

			// Select Small Business Tab
			else if (tab.equals("Small Business")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Mobiles']"))).click();
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[text()='" + mobile + "']/ancestor::a"))).click();
				System.out.println(mobile + " product detail page is displayed successfully");
				String temp = Library.getScreenShot(driver, mobile + " product detail page");
				logger.log(Status.PASS, mobile + " product detail page is displayed successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath("//*[@id='keyFeatures']/following-sibling::div//button[@data-colour='" + colour + "']")))
						.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//input[@value='" + storage + "' and @name='storageSize']/parent::label"))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//input[@value='" + planType + "' and @name='productDetailPlans']/parent::label")))
						.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='" + plan + "']")))
						.click();
				String temp1 = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, plan + " plan is under " + planType + " is selected",
						MediaEntityBuilder.createScreenCaptureFromPath(temp1).build());
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//h3[text()='" + plan + "']//following::a[text()='Buy now']"))).click();

				if (Library.isElementPresent(By.id("cheAddToCart"))) {
					System.out.println(mobile + " is ready to be added to cart");
					String temp2 = Library.getScreenShot(driver, plan + " plan is under " + planType + " is selected");
					logger.log(Status.PASS, mobile + " is ready to be added to cart",
							MediaEntityBuilder.createScreenCaptureFromPath(temp2).build());
				} else {
					System.out.println(mobile + " is not ready to be added to cart");
					String temp3 = Library.getScreenShot(driver, mobile + " is not added to cart");
					logger.log(Status.FAIL, mobile + " is not added to cart successfully",
							MediaEntityBuilder.createScreenCaptureFromPath(temp3).build());
				}
			}

		} catch (Exception e) {
			System.out.println(mobile + " is not added to cart successfully, " + colour
					+ " colour is not available for " + mobile);
			String temp = Library.getScreenShot(driver, "name");
			logger.log(Status.FAIL,
					mobile + " is not added to cart successfully, " + colour + " colour is not available for " + mobile,
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public static void buyBusinessPlanPage(ExtentTest logger) throws Exception {

		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);

			// Select Business Bundle Page
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Business Bundles']")))
					.click();
			if (Library.isElementPresent(By.id("sq-address"))) {
				System.out.println("Business bundles page is displayed");
				String temp = Library.getScreenShot(driver, "Business bundles page is displayed");
				logger.log(Status.PASS, "Business bundles page is displayed",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				System.out.println("Business bundles page is not displayed");
				String temp3 = Library.getScreenShot(driver, "Business bundles page is not displayed");
				logger.log(Status.FAIL, "Business bundles page is not displayed",
						MediaEntityBuilder.createScreenCaptureFromPath(temp3).build());
			}
		} catch (Exception e) {
			System.out.println("Business bundles page is not displayed");
			String temp = Library.getScreenShot(driver, "Business bundles page is not displayed");
			logger.log(Status.FAIL, "Business bundles page is not displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void buyBusinessPlan(String address, ExtentTest logger) throws Exception {

		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			JavascriptExecutor js = (JavascriptExecutor) driver;

			String[] arrState = address.split(";");
			for (int i = 0; i < arrState.length; i++) {

				if (Library.isElementPresent(By.id("cheAddToCart"))) {
					driver.findElement(By.xpath("//span[@class='site-logo']")).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Small Business']")))
							.click();
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//span[text()='Business Bundles']"))).click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='sq-reset']")))
							.click();
				}

				js.executeScript("window.scrollBy(0,1000)");

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sq-address"))).sendKeys(arrState[i]);
				waitForPageLoaded(20);
				System.out.println(arrState[i]);
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//span[@class='tt-dropdown-menu']/div[@class='tt-dataset-sqSuggest']"))).click();

				if (wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='View plans']"))) != null) {

					String planmsg = driver.findElement(By.xpath("//div[@class='sub-heading sq-title']/span[2]"))
							.getText();
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='View plans']"))).click();
					System.out.println("For the adress entered- " + arrState[i] + ", plan is available");
					String temp = Library.getScreenShot(driver,
							"For the adress entered- " + arrState[i] + ", plan is available");
					logger.log(Status.PASS, "For the adress entered- " + arrState[i] + ", plan is available",
							MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

					if (planmsg.contains("ADSL")) {
						driver.findElement(By.xpath(
								"//div[@class='standard row content smlxl-plans total-smlxl-plans-2 carousel']//div[@class='smlxlCtaRow section']//div[6]/a"))
								.click();
					} else if (planmsg.contains("nbn")) {
						driver.findElement(By.xpath(
								"//div[@class='plan-description gravur-bold']//following::div[@class='parsys smlxl_par_s']//div[9]/a"))
								.click();
					}

					if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cheAddToCart"))) != null) {
						System.out.println("Bundle is ready to be added to cart");
						String temp2 = Library.getScreenShot(driver, "Bundle is ready to be added to cart");
						logger.log(Status.PASS, "Bundle is ready to be added to cart",
								MediaEntityBuilder.createScreenCaptureFromPath(temp2).build());
					} else {
						System.out.println("Bundle is ready to be added to cart");
						String temp4 = Library.getScreenShot(driver, "Bundle is ready to be added to cart");
						logger.log(Status.FAIL, "Bundle is ready to be added to cart",
								MediaEntityBuilder.createScreenCaptureFromPath(temp4).build());
					}
				}
			}
		} catch (Exception e) {
			System.out.println("No plan available for Address entered");
			String temp = Library.getScreenShot(driver, "No plan available for Address entered");
			logger.log(Status.FAIL, "No plan available for Address entered",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void Login(String strUsername, String strPassword, ExtentTest logger) throws Exception {

		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		try {
			// Enter Username
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(strUsername);
			System.out.println("Username Entered - " + strUsername);
			strComments = strComments + "\nUsername Entered -" + strUsername;

			// Enter PASSword
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(strPassword);
			System.out.println("Password Entered - " + strPassword);
			strComments = strComments + "\nPassword Entered -" + strPassword;

			driver.findElement(By.id("Login")).click();
			Thread.sleep(5000);
			if (Library.isElementPresent(By.id("tryLexDialogX"))) {
				driver.findElement(By.id("tryLexDialogX")).click();
			}

			// Verifying Login Functionality
			if (Library.isElementPresent(By.id("AllTab_Tab"))) {
				System.out.println("Login is done successfully");
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "Login is done successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {

				String temp = Library.getScreenShot(driver, name);
				System.out.println("Login is not done successfully");
				logger.log(Status.FAIL, "Login is not done successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}

		} catch (Exception e) {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "Login is not done successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public static void Logout(ExtentTest logger) throws Exception {

		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		try {
			waitForPageLoaded(20);
			if (Library.isElementPresent(By.id("userNavLabel"))) {
				driver.findElement(By.id("userNavLabel")).click();
			} else if (Library.isElementPresent(By.id("globalHeaderNameMink"))) {
				driver.findElement(By.id("globalHeaderNameMink")).click();
			}
			driver.findElement(By.xpath("//a[@href='/secur/logout.jsp']")).click();
			waitForPageLoaded(20);
			if (Library.isElementPresent(By.id("username"))) {
				System.out.println("Logout is done successfully");
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "Logout is done successfully",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

			} else {
				System.out.println("Error:Logout is not done");
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.FAIL, "Error:Logout is not done",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

			}
		} catch (Exception e) {
			System.out.println("Error:Logout is not done");
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "Error:Logout is not done",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

		}
	}

	public static void verifyQuestion(String question, ExtentTest logger) throws Exception {
		try {

			waitForPageLoaded(20);

			String element1 = null;
			String element3 = question + "']";
			element1 = "//legend[text()='" + element3.replace("\n", "");
			String questionValue = question.replace("\n", "");
			String questionAct = driver.findElement(By.xpath(element1)).getText().trim();
			System.out.println(questionAct);

			if (questionValue.equals(questionAct)) {

				System.out.println(questionAct + " , Question is displayed");
				String temp = Library.getScreenShot(driver, questionAct.replace("?", "") + " , Question is displayed");
				logger.log(Status.PASS, questionAct + " , Question is displayed",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

			} else {
				System.out.println(question + " , Question is not displayed");
				String temp = Library.getScreenShot(driver, question.replace("?", "") + " , Question is not displayed");
				logger.log(Status.FAIL, question + " , Question is not displayed",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

		catch (Exception e) {
			System.out.println("Question is not displayed");
			String temp = Library.getScreenShot(driver, "Question is not displayed");
			logger.log(Status.FAIL, "Question is not displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void verifyOptions(String options, ExtentTest logger) throws Exception {
		try {

			waitForPageLoaded(20);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			String[] optionsValue = options.split(";");

			for (int i = 1; i <= optionsValue.length; i++) {

				String element1 = null;
				String element3 = "]//label";

				element1 = "//div[@class='ctrl-holder radio-button-group']//div[" + i + element3;
				String optionsValueAct = driver.findElement(By.xpath(element1)).getText().trim();

				System.out.println(optionsValueAct);

				if (optionsValueAct.equals(optionsValue[i - 1])) {
					WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element1)));
					driver.findElement(By.xpath(element1)).click();

					System.out.println(optionsValueAct + " , Option is listed for the question");
					String temp = Library.getScreenShot(driver,
							optionsValueAct + " , Option is listed for the question");
					logger.log(Status.PASS, optionsValueAct + " , Option is listed for the question",
							MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

				} else {
					System.out.println(optionsValue + " , Option is not listed for the question");
					String temp = Library.getScreenShot(driver,
							optionsValue + " , Option is not listed for the question");
					logger.log(Status.FAIL, optionsValue + " , Option is not listed for the question",
							MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
				}
			}
		}

		catch (Exception e) {
			System.out.println("Option is not listed for the question");
			String temp = Library.getScreenShot(driver, "Option is not listed for the question");
			logger.log(Status.FAIL, "Option is not listed for the question",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

		}

	}

	public static void selectOption(String option, ExtentTest logger) throws Exception {
		try {

			waitForPageLoaded(20);

			String element1 = "//label[text()='" + option + "']".replace("\n", "");

			if (isElementPresent(By.xpath(element1)))

			{
				driver.findElement(By.xpath(element1)).click();
				driver.findElement(By.xpath("//button[text()='Next']")).click();
				System.out.println(option + " , Option is selected");
				String temp = Library.getScreenShot(driver, option + " , Option is selected");
				logger.log(Status.PASS, option + " , Option is selected",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				System.out.println(option + " , Option is not selected");
				String temp = Library.getScreenShot(driver, option + " , Option is not selected");
				logger.log(Status.FAIL, option + " , Option is not selected",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

		catch (Exception e) {
			System.out.println(option + " , Option is not selected");
			String temp = Library.getScreenShot(driver, option + " , Option is not selected");
			logger.log(Status.FAIL, option + " , Option is not selected",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void verifySolution(String option, ExtentTest logger) throws Exception {
		try {

			waitForPageLoaded(20);

			String element1 = "//h2[text()='" + option + "']".replace("\n", "");

			if (isElementPresent(By.xpath(element1)))

			{
				String solutionText = driver.findElement(By.xpath(element1)).getText().trim();
				System.out.println(solutionText + " , Solution is displayed");
				String temp = Library.getScreenShot(driver, solutionText + " , Solution is displayed");
				logger.log(Status.PASS, solutionText + " , Solution is displayed",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				System.out.println("Solution is not displayed");
				String temp = Library.getScreenShot(driver, "Solution is not displayed");
				logger.log(Status.FAIL, "Solution is not displayed",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

		catch (Exception e) {
			System.out.println("Solution is not displayed");
			String temp = Library.getScreenShot(driver, "Solution is not displayed");
			logger.log(Status.FAIL, "Solution is not displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void verifyInstructionTile(String compElement, String iconElement, String intCompElement,
			String compSize, String iconSize, String intCompSize, String hLevel, ExtentTest logger) throws Exception {
		try {

			waitForPageLoaded(20);

			getSize(intCompElement, intCompSize, "Instruction Tile Component", logger);
			getSize(compElement, compSize, "Instruction Tile", logger);
			getSize(iconElement, iconSize, "Icon", logger);

			String heading = compElement + "//div[@class='tile-content']//" + hLevel + "[@class='tile-headline']";

			if (isElementPresent(By.xpath(heading)))

			{
				System.out.println("Heading level of the Step indicator is :" + hLevel);
				String temp = Library.getScreenShot(driver, "Heading level of the Step indicator is ," + hLevel);
				logger.log(Status.PASS, "Heading level of the Step indicator is : " + hLevel,
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				System.out.println("Heading level of the Step indicator is not as expected");
				String temp = Library.getScreenShot(driver, "Heading level of the Step indicator is not as expected");
				logger.log(Status.FAIL, "Heading level of the Step indicator is not as expected",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

		catch (Exception e) {
			System.out.println("Error: Instruction Tile is not displayed as expected");
			String temp = Library.getScreenShot(driver, "Error- Instruction Tile is not displayed as expected");
			logger.log(Status.FAIL, "Error: Instruction Tile is not displayed as expected",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public static void verifyLink(String linkElement, String linkURL, ExtentTest logger) throws Exception {
		try {

			waitForPageLoaded(20);

			String actualLinkValue = driver.findElement(By.xpath(linkElement)).getAttribute("href").trim();
			System.out.println(actualLinkValue);

			if (linkURL.equals(actualLinkValue))

			{
				System.out.println("Actual & Expected values of link URL have matched, value is " + linkURL);
				String temp = Library.getScreenShot(driver, "Actual & Expected values of link URL have matched, value");
				logger.log(Status.PASS, "Actual & Expected values of link URL have matched, value is " + linkURL,
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				System.out.println("Actual & Expected values of link URL have not matched");
				String temp = Library.getScreenShot(driver, "Actual & Expected values of link URL have not matched");
				logger.log(Status.FAIL, "Actual & Expected values of link URL have not matched",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

		catch (Exception e) {
			System.out.println("Error: Actual & Expected values of link URL have not matched");
			String temp = Library.getScreenShot(driver, "Error- Actual & Expected values of link URL have not matched");
			logger.log(Status.FAIL, "Error- Actual & Expected values of link URL have not matched",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}
}