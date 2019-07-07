package shoppingCart.pagesObject;

import Utility_PO.FailTest;
import Utility_PO.Logger;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.google.common.base.Function;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasePage {

	public static WebDriver driver;

	public static final int DEFAULT_WAIT_TIME = 10;

	public WebDriver getDriver() {
		return driver;
	}

	TreeMap<String, String> dataStream;
	FailTest fail;
	ITestContext ctx;
	Logger testData;

	public void setDriver(String browserType, String appURL) {
		switch (browserType) {
		case "Chrome":
			driver = initChromeDriver(appURL);
			break;
		case "Firefox":
			driver = initFirefoxDriver(appURL);
			break;
		case "Headless":
			driver = initHeadlessDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox Now..");
			driver = initFirefoxDriver(appURL);
		}
	}

	@BeforeClass
	@Parameters({ "browserType", "appURL" })
	public void initializeTestBaseSetup(String browserType, String appURL) {

		try {
			setDriver(browserType, appURL);

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}

	public static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching chrome browser..");
		WebDriverManager.chromedriver().version("2.46").setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	public static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		System.setProperty("webdriver.gecko.driver", "/Automation/Driver/geckodriver.exe");
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	public static WebDriver initHeadlessDriver(String appURL) {
		System.out.println("Launching Headless browser..");
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
		driver.navigate().to(appURL);
		return driver;
	}

	public void click(By elementLocation) {
		try {

			WebElement clickAction = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(elementLocation));
			clickAction.click();
			Thread.sleep(3000);
		} catch (Exception e) {

			fail.failTest(e + " : " + "Timed out waiting for click: " + elementLocation);
		}
	}

	public String readText(By elementLocation) throws InterruptedException {
		Thread.sleep(2000);
		return driver.findElement(elementLocation).getText();

	}

	public void writeText(By elementLocation, String text) {

		try {

			WebElement enterText = new WebDriverWait(driver, 60)
					.until(ExpectedConditions.elementToBeClickable(elementLocation));
			enterText.sendKeys(text);
			Thread.sleep(1000);

		} catch (Exception e) {

			fail.failTest(e + " : " + "Timed out waiting for enter text: " + elementLocation);
		}
	}

	public void delayFor(int timeInMili) {
		try {
			Thread.sleep(timeInMili);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		delayFor(3000);
	}

	public String data(String label) {
		return dataStream.get(label).toString();

	}

	public void verifyText(By by, String textToVerify) {
		WebElement element = waitForElementDisplayed(by, 10);
		highlight(element);
		String actualText = element.getText();
		assertThat(actualText, equalTo(textToVerify));
	}

	public WebElement waitForElementDisplayed(final By locator, int timeToWaitInSec) {

		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeToWaitInSec, TimeUnit.SECONDS)
				.pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(locator);
				if (element != null && element.isDisplayed()) {
					return element;
				}
				return null;
			}
		});

		driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.MILLISECONDS);
		return foo;
	}

	protected void highlight(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid red;");
			delayFor(200);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			delayFor(200);
		}
	}

	@AfterClass
	public void tearDown() {

		// driver.quit();
	}

}