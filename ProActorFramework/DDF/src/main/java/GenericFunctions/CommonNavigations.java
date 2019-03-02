package GenericFunctions;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class CommonNavigations {

	public static WebDriver driver;

	public String Dropdown_xpath1 = "//a[@title='";
	public String Dropdown_xpath2 = "']";

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'No results')]")
	public List<WebElement> NoResults_xpath;

	@FindBy(how = How.XPATH, using = "//a[@href='#tab-personal']")
	public WebElement PersonalTab;

	@FindBy(how = How.XPATH, using = "//li/button[contains(@class,'branding-userProfile-button')]")
	public WebElement UserIconImg_xpath;

	@FindBy(how = How.XPATH, using = "//a[text()='Log Out']")
	public WebElement LogoutLink_xpath;

	@FindBy(how = How.XPATH, using = "(//a[text()='Switch to Salesforce Classic'])[1]")
	public WebElement SwitchtoClassic_xpath;

	@FindBy(how = How.XPATH, using = "//span[contains(@class,'globalHeaderProfilePhoto')]")
	public WebElement headerprofile_xpath;

	@FindBy(how = How.XPATH, using = "//span[contains(@class,'globalHeaderProfilePhoto')]")
	public List<WebElement> headerprofilelist_xpath;

	@FindBy(how = How.XPATH, using = "//div[@class='linkElements']/a[text()='Switch to Lightning Experience']")
	public WebElement Switchtolight_xpath;

	@FindBy(how = How.XPATH, using = "//img[@title='User']")
	public List<WebElement> Lightninguserimage_xpath;

	@FindBy(how = How.XPATH, using = "//input[@title='Search Salesforce']")
	public WebElement GlobalSearch_xpath;

	@FindBy(how = How.XPATH, using = "//nav[contains(@class,'appLauncher')]/one-app-launcher-header/button")
	public WebElement Applauncher_xpath;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Find an app or item']")
	public WebElement SearchApp_xpath;

	public String ObjectTab1_xpath = "//span[contains(@class,'label')][text()='";
	public String ObjectTab2_xpath = "']";

	public String ObjectScreen1_xpath = "//span[@class='uiOutputText'][text()='";
	public String ObjectScreen2_xpath = "']";

	public void Search(String Searchvalue, ExtentTest logger) throws Exception

	{
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		WebDriverWait wait = new WebDriverWait(driver, 50);
		driver.navigate().refresh();
		Thread.sleep(5000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search Salesforce']")))
				.clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search Salesforce']")))
				.sendKeys(Searchvalue/* , Keys.ENTER */);

		Thread.sleep(3000);
		List<WebElement> Results = driver
				.findElements(By.xpath(WebElementLocators.GetGlobalSearchLookupDropdown(Searchvalue)));
		System.out.println("Results=" + Results.size());
		if (Results.size() > 0) {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(WebElementLocators.GetGlobalSearchLookupDropdown(Searchvalue)))).click();
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "Searched result is displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

		} else {

			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "There are no results for Searched value",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public void SearchandClickrecord(String Searchvalue, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		Search(Searchvalue, logger);
		Thread.sleep(5000);
		driver.navigate().refresh();

		Thread.sleep(5000);

		List<WebElement> NoResults = driver.findElements(By.xpath("(//span[text()='" + Searchvalue + "'])[1]"));
		System.out.println("NoResults: " + NoResults.size());
		if (NoResults.size() == 0) {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "There are no results for Searched value",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "Searched result is displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public void Logout(WebDriver driver) throws Exception {
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOf(LightningWE.UserIconImg_xpath)).click();
		wait.until(ExpectedConditions.visibilityOf(LightningWE.LogoutLink_xpath)).click();

	}

	public void SwitchtoClassic(String folder, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOf(LightningWE.UserIconImg_xpath)).click();
		wait.until(ExpectedConditions.visibilityOf(LightningWE.SwitchtoClassic_xpath)).click();
		WebElement Switchtolight = wait.until(ExpectedConditions.visibilityOf(LightningWE.Switchtolight_xpath));
		if (Switchtolight.isDisplayed()) {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "Switching from Lighting to Classic successful",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "There are no results for Searched value",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public void SwitchtoLightning(ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		List<WebElement> headerlist = headerprofilelist_xpath;
		List<WebElement> Lightimglist = Lightninguserimage_xpath;
		if (headerlist.size() > 0) {
			wait.until(ExpectedConditions.visibilityOf(LightningWE.headerprofile_xpath)).click();
			wait.until(ExpectedConditions.visibilityOf(LightningWE.Switchtolight_xpath)).click();

			WebElement Applaunch = wait.until(ExpectedConditions.visibilityOf(LightningWE.Applauncher_xpath));

			if (Applaunch.isDisplayed()) {
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "Switching from Classic to Lighting successful",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.FAIL, "Switching from Classic to Lighting unsuccessful",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

		else if (Lightimglist.size() > 0) {
			WebElement Applaunch = wait.until(ExpectedConditions.visibilityOf(LightningWE.Applauncher_xpath));

			if (Applaunch.isDisplayed()) {
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "Switching from Classic to Lighting successful",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.FAIL, "Switching from Classic to Lighting unsuccessful",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}
		}

	}

	public void SearchandClickObjectTab(String Object, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);

		String ObjecttabXpath = ObjectTab1_xpath + Object + ObjectTab2_xpath;
		String ObjectScreenXpath = ObjectScreen1_xpath + Object + ObjectScreen2_xpath;
		wait.until(ExpectedConditions.visibilityOf(LightningWE.Applauncher_xpath)).click();
		/*
		 * wait.until( ExpectedConditions
		 * .visibilityOf(LightningWE.SearchApp_xpath)) .sendKeys(Object);
		 */
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ObjecttabXpath))).click();

		if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ObjectScreenXpath))).isDisplayed()) {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "The " + Object + " Screen is displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "The " + Object + " Screen is not displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public void ChangeOwner(String OwnerName, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Change Owner']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search People...']")))
				.sendKeys(OwnerName);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(WebElementLocators.getLookupDropdown(OwnerName))))
				.click();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[contains(@class,'label')][text()='Change Owner']")))
				.click();
		Thread.sleep(3000);
	}

	public void SwitchTabsinLightning(String Tabname, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement e = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(WebElementLocators.getTabs(Tabname))));
		e.click();
		Thread.sleep(3000);
		String value = e.getAttribute("aria-selected");
		System.out.println(value);
		if (value.equalsIgnoreCase("true")) {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "The tab is displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "The tab is not displayed",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
	}

	public void ClickonResourceCard(String linktext, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElementLocators LE = new WebElementLocators();
		driver.navigate().refresh();
		SwitchTabsinLightning("Resources", logger);
		Thread.sleep(5000);
		List<WebElement> linkPresent = driver
				.findElements(By.xpath(WebElementLocators.GetResourceCardLink(linktext)));

		if (linkPresent.size() > 0) {
			for (WebElement e : linkPresent) {
				// e.click();
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click()", e);
				Thread.sleep(5000);
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "The flashcard is clicked",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
				break;
			}
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "The flashcard is not present",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public void switchResourcecardIframe(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.navigate().refresh();
		wait.until(ExpectedConditions
				.frameToBeAvailableAndSwitchToIt(By.xpath("//div[@class='slds-template_iframe slds-card']/iframe")));
	}

	public void ClickonRelatedList(String RelatedlistName, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		SwitchTabsinLightning("Related", logger);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Show All')]")))
				.click();

		List<WebElement> Relatedlistlink = driver
				.findElements(By.xpath(WebElementLocators.RelatedlistLink(RelatedlistName)));

		if (Relatedlistlink.size() > 0) {
			for (WebElement e : Relatedlistlink) {
				e.click();
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "The Relatedlist " + RelatedlistName + " is clicked",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
				break;
			}
		} else {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.FAIL, "The Relatedlist " + RelatedlistName + " is not present",
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

	}

	public void LoginProcess(WebDriver driver) throws Exception {
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);
		WebDriverWait wait = new WebDriverWait(driver, 50);

		List<WebElement> Userdetailbutton = driver
				.findElements(By.xpath(WebElementLocators.getTopPanelButton("User Detail")));

		if (Userdetailbutton.size() == 0) {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[title*=User]")));
			wait.until(ExpectedConditions.visibilityOf(LightningWE.PersonalTab)).click();
			Thread.sleep(5000);
			driver.switchTo().defaultContent();

		}

		else {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(WebElementLocators.getTopPanelButton("User Detail"))))
					.click();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[title*=User]")));
			wait.until(ExpectedConditions.visibilityOf(LightningWE.PersonalTab)).click();
			driver.switchTo().defaultContent();

		}
	}

	public void LoginasUser(String Searchvalue, ExtentTest logger) throws Exception {
		String name = new Object() {
		}.getClass().getEnclosingMethod().getName();
		CommonNavigations LightningWE = new CommonNavigations();
		LightningWE = PageFactory.initElements(driver, CommonNavigations.class);

		Search(Searchvalue, logger);
		Thread.sleep(5000);
		LoginProcess(driver);
		List<WebElement> LoggedinUser = driver.findElements(By.xpath("//span[contains(text(),'" + Searchvalue + "')]"));
		List<WebElement> NotLogged = driver.findElements(
				By.xpath("//h1[contains(@class,'noSecondHeader')][text()='Admin Users (Full System Admin)']"));

		if (LoggedinUser.size() > 0) {
			String temp = Library.getScreenShot(driver, name);
			logger.log(Status.PASS, "User is logged in", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

		} else if (NotLogged.size() > 0) {
			Search(Searchvalue, logger);
			Thread.sleep(5000);
			LoginProcess(driver);
			if (LoggedinUser.size() > 0) {
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.PASS, "User is logged in",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} else {
				String temp = Library.getScreenShot(driver, name);
				logger.log(Status.FAIL, "User is not logged in",
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			}

		}

	}

}
