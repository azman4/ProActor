package GenericFunctions;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionOnElements {

	static String sheetName = null;
	static int sheetVal = 0;

	public static void SetSheetDetails(String sheetName, int sheetVal) {

		ActionOnElements.sheetName = sheetName;
		ActionOnElements.sheetVal = sheetVal;

	}

	public void enterTextBox(WebDriver driver, String labelName) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.selectSection(labelName))))
					.clear();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.selectSection(labelName))))
					.sendKeys(excelValue);
		}

	}

	public void enterTextArea(WebDriver driver, String labelName) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getTextArea(labelName))))
					.clear();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getTextArea(labelName))))
					.sendKeys(excelValue);
		}

	}

	public String enterTextBoxWithRandomData(WebDriver driver, String labelName, int randomInteger)
			throws ParseException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);
		String NewLegalName = null;
		if (excelValue != null && !excelValue.isEmpty()) {

			// long randomInteger = Generic_Functions.randomFunctionGenerator();
			NewLegalName = excelValue + randomInteger;
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.selectSection(labelName))))
					.clear();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.selectSection(labelName))))
					.sendKeys(NewLegalName);
		}
		return NewLegalName;
	}

	public String selectPicklist(WebDriver driver, String labelName) {

		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			WebElementLocators.SelectValueinPicklist(driver, labelName, excelValue);
		}
		return excelValue;
	}

	//Combobox - Azman
	public String enterCombobox(WebDriver driver, String labelName) {

		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		if (excelValue != null && !excelValue.isEmpty()) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.comboBox(labelName))))
					.clear();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.comboBox(labelName))))
					.sendKeys(excelValue);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.comboBox(labelName))))
					.sendKeys(Keys.TAB);
		}
		return excelValue;

	}
	
	//ComboboxTime - Azman
		public String enterComboboxTime(WebDriver driver, String labelName) {

			String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

			WebDriverWait wait = new WebDriverWait(driver, 30);
			if (excelValue != null && !excelValue.isEmpty()) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(WebElementLocators.comboBoxTime(labelName))))
						.clear();
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(WebElementLocators.comboBoxTime(labelName))))
						.sendKeys(excelValue);
			}
			return excelValue;

		}
	
	public void selectMandatoryPicklist(WebDriver driver, String labelName) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);
		WebElementLocators LightningWE = new WebElementLocators();

		if (excelValue != null && !excelValue.isEmpty()) {

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getPicklist(labelName)))).click();
			Thread.sleep(2000);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(LightningWE.Dropdown_xpath1 + excelValue + LightningWE.Dropdown_xpath2))).click();
		}
	}

	public void selectMultiPicklist(WebDriver driver, String labelName) {

		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			WebElementLocators.SelectValueinMultiPicklist(driver, labelName, excelValue);
		}

	}

	public void selectTodaysDate(WebDriver driver, String labelName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getDateIcon(labelName)))).click();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getDateSelection()))).click();
		}
	}
	
	//Enter date - Azman
	
	public void enterDate(WebDriver driver, String labelName) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    Date date = new Date(); 
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.selectSection(labelName))))
					.clear();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.selectSection(labelName))))
					.sendKeys(formatter.format(date));
		}

	}

	//Enter date - Azman
	
		public void enterDateActivities(WebDriver driver, String labelName) {

			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		    Date date = new Date(); 
			WebDriverWait wait = new WebDriverWait(driver, 30);
			String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

			if (excelValue != null && !excelValue.isEmpty()) {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(WebElementLocators.getDateBoxActivities(labelName))))
						.clear();
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(WebElementLocators.getDateBoxActivities(labelName))))
						.sendKeys(formatter.format(date));
			}

		}
		
	public void selectLookupValue(WebDriver driver, String labelName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			
			List<WebElement> ValuePresent = driver.findElements(By.xpath(WebElementLocators.getLookupvalueDeleteicon(labelName)));
			if(ValuePresent.size()>0){
				for(WebElement E :ValuePresent )
				{
					E.click();
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
							.sendKeys(excelValue);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupDropdown(excelValue))))
							.click();
					break;
				}
			}
			
			else{
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
					.sendKeys(excelValue);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupDropdown(excelValue))))
					.click();
			}

		}
	}
	
	//Azman- New
	public void selectLookupValueActivities(WebDriver driver, String labelName) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			
			List<WebElement> ValuePresent = driver.findElements(By.xpath(WebElementLocators.getLookupvalueDeleteicon(labelName)));
			if(ValuePresent.size()>0){
				for(WebElement E :ValuePresent )
				{
					E.click();
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
							.sendKeys(excelValue);
					Thread.sleep(2000);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupDropdown(excelValue))))
							.click();
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
							.sendKeys(Keys.TAB);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
							.sendKeys(Keys.TAB);
					break;
				}
			}
			
			else{
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
					.sendKeys(excelValue);
			Thread.sleep(2000);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupDropdown(excelValue))))
					.click();
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupInputBox(labelName))))
					.sendKeys(Keys.TAB);
			}

		}
	}
	public void selectCheckbox(WebDriver driver, String labelName) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String excelValue = Library.getParameterFromInputSheet(sheetName, labelName, sheetVal);

		if (excelValue != null && !excelValue.isEmpty()) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getCheckbox(labelName)))).click();

		}
	}

	public void clickElement(WebDriver driver, String xpathOfElement) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfElement))).click();

	}

	//Azman - new
	public static void pressTab(WebDriver driver) throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
}
