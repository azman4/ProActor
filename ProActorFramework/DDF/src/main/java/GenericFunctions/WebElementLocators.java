package GenericFunctions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementLocators {

	//Select a tab on Home screen
	public static String selectTab(String labelName) {

		return "//p[text()='" + labelName + "']/parent::a";
	}

	//Select a section on a tab
	public static String selectSection(String labelName) {

		return "//span[text()='" + labelName + "']/ancestor::a";

	}
	
	public static String PersonalTab() {

		return "//a[@href='#tab-personal']/parent::li";
	}
	
	//get date box - Azman
		public static String getDateBoxActivities(String labelName) {

			return "//span[text()='" + labelName + "']/parent::legend/following-sibling::div//input";

		}
	
	public static String getPicklist(String labelName) {

		return "//span[text()='" + labelName + "']/parent::*[contains(@class,'label')]/following-sibling::div//a";

	}

	public static String getLookupInputBox(String labelName) {

		return "//span[text()='" + labelName + "']/parent::label/following-sibling::div//input";

	}
	
	public static String getLookupvalueDeleteicon(String labelName) {

		return "//span[text()='" + labelName + "']/parent::label/following-sibling::div//a[@class='deleteAction']/span";

	}

	public static String getLookupSelection(String searchValue) {

		return "//div[@title='" + searchValue + "']";

	}

	public static String getMultiPicklist(String labelName) {

		return "//span[text()='" + labelName + "']/parent::label/following-sibling::select";

	}

	public static String getTopPanelButton(String labelName) {

		return "//div[contains(@class,'slds-page-header')]//a/div[text()='" + labelName + "']";

	}

	public static String getRelatedListButton(String labelName) {

		return "//div[@data-aura-class='forceRelatedListContainer']//span[@title='" + labelName
				+ "']/ancestor::header/following-sibling::div//a/div[text()='New']";

	}

	public static String getTextViewMode(String labelName) {

		return "//*[text()='" + labelName + "']//following::span[2]";

	}

	public static String getFlashCardLink(String linkText, String ResourceCardHeader) {

		return "//*[@class=’slds-card cResourceCard’]//span[text()='" + ResourceCardHeader + "']//following::*[text()='"
				+ linkText + "']";

	}

	public static String GetResourceCardLink(String linkText) {

		return "//div[@class='flexipageComponent']/div[@data-aura-class='cResourceCard']//lightning-icon[@title='"
				+ linkText + "']/lightning-primitive-icon";

	}

	public static String getDetailScreenLink(String linkText) {

		return "//*[text()='" + linkText + "']//following::a[1]";

	}

	public static String getCheckbox(String labelName) {

		return "//span[text()='" + labelName + "']/parent::label/following-sibling::input";

	}

	public static String getDateIcon(String labelName) {

		return "//span[text()='" + labelName + "']/parent::label/following-sibling::div/a";

	}

	//Date Icon on Activities box - Azman
	public static String getDateIconActivities(String labelName) {

		return "//span[text()='" + labelName + "']/parent::legend/following-sibling::div/a";

	}
	
	public static String getDateSelection() {

		return "//*[text()='Today']";

	}

	public static String getRadioButton(String labelName) {

		return "//*[text()='" + labelName + "']/parent::div/preceding-sibling::div/span[@class='slds-radio--faux']";

	}

	public static String getToggle(String labelName) {

		return "//*[text()='" + labelName + "']//preceding::*[@class='slds-checkbox_faux']";

	}

	public static String getTabs(String tabName) {

		return "//span[@class='title'][text()='" + tabName + "']//ancestor::a";

	}

	public static String RelatedlistLink(String RelatedlistName) {

		return "//a[@class='rlql-relatedListLink']/span[contains(text(),'" + RelatedlistName + "')]";

	}

	public static String getAcHierarchyTab(String tabName) {
		return "//a[text()='" + tabName + "']";
	}
	
	//Combobox - Azman
	public static String comboBox(String labelName) {
		return "//span[text()='" + labelName + "']/parent::label/following-sibling::lightning-grouped-combobox//input";
	}
	
	//ComboboxTime - Azman
	public static String comboBoxTime(String labelName) {
		return "//span[text()='" + labelName + "']/parent::legend/following-sibling::div/div[2]//input";
	}
	
	// Code by Pawan
	@FindBy(how = How.XPATH, using = "//a[@title='New']")
	public WebElement NewBtn_xpath;

	@FindBy(how = How.XPATH, using = "//span[text()='Next']")
	public WebElement NextBtn_xpath;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'forceActionButton') and contains(@class,'uiButton--default') ][@title='Save']")
	public WebElement SaveBtn_xpath;
	
	//Add button on Account Page (Log a call) - Azman
	@FindBy(how = How.XPATH, using = "//button[@title='Add']")
	public WebElement AddBtn_xpath;
	
	//Add button on Account Page (Log a call) - AzmanNew
		@FindBy(how = How.XPATH, using = "//span[text()='Save']/parent::button[@class='slds-button slds-button--brand cuf-publisherShareButton SMALL uiButton']")
		public WebElement SaveBtnActivities_xpath;
	
	public String Dropdown_xpath1 = "//li[@role='presentation']/a[@title='";
	public String Dropdown_xpath2 = "']";

	public static String SearchedValueLink(String SearchedValue) {

		return "//a[contains(@class,'outputLookupLink')][contains(@data-ownerid,'a')][text()='" + SearchedValue + "']";

	}

	public static String SearchedNameLink(String SearchedValue) {

		return "//div[@class='name']/div/a[text()='" + SearchedValue + "']";

	}

	public static String getTextArea(String Labelname) {

		return "//span[text()='" + Labelname + "']/parent::label/following-sibling::textarea";

	}

	public static String getLookupDropdown(String searchValue) {

		return "(//div[contains(@class,'slds-lookup__result-text')][@title='" + searchValue + "'])[1]";
		// "//mark[@class='data-match'][text()='"+searchValue+"']";

	}

	public static String GetGlobalSearchLookupDropdown(String searchValue) {

		return "(//span[contains(@class,'mruName')][@title='" + searchValue + "'])[1]";
		// "//mark[@class='data-match'][text()='"+searchValue+"']";

	}

	public static void SelectValueinPicklist(WebDriver driver, String PicklistLabelname, String PicklistValue) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement ele = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getPicklist(PicklistLabelname))));
		((JavascriptExecutor) driver).executeScript("var ele=arguments[0]; ele.innerHTML = '" + PicklistValue + "';",
				ele);
		System.out.println(ele.getText());

	}

	public static void SelectValueinMultiPicklist(WebDriver driver, String MultiPicklistLabelname,
			String MultiPicklistValue) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Select multipick = new Select(wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(getMultiPicklist(MultiPicklistLabelname)))));
		multipick.selectByVisibleText(MultiPicklistValue);

	}
}
