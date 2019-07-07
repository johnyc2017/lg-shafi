package shoppingCart.test;

import Utility_PO.FailTest;
import Utility_PO.Logger;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import shoppingCart.pagesObject.BasePage;
import shoppingCart.pagesObject.LoginPage;
import shoppingCart.pagesObject.ShoppingCartSummary_PO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.util.TreeMap;

public class ShoppingCartSummaryTest extends BasePage {

	public WebDriver driver;
	public WebDriverWait wait;

	TreeMap<String, String> dataStreamSetup = new TreeMap<String, String>();
	TreeMap<String, String> dataStream = new TreeMap<String, String>();
	File tempOutputFile = new File("C:\\temp\\AutomationPractice_logs\\ShoppingCartSummary.txt");
	FailTest fail;

	BufferedWriter writer;
	ITestContext ctx;
	Logger testData;
	Logger alertLogger;
	Logger msgLogger;

	// Test Data
	String Username = "lookingglastest@mailinator.com";
	String Password = "abcd1234";

	@Severity(SeverityLevel.BLOCKER)
	@Description("Validate Add Dresses and Shopping-Cart Summary")
	@Story("Validate Shopping Cart")
	@Test
	public void ShoppingCartSummeryTests() throws Throwable {

		final boolean[] asyncExecuted = { false };
		final Throwable[] asyncThrowable = { null };

		// async Process entry

		new Thread(new Runnable() {
			@Override

			public void run() {

				// Test execution starts from here
				try {

					ShoppingCartSummary_PO shoppingCartSummaryPage = new ShoppingCartSummary_PO(driver, wait);
					LoginPage loginPage = new LoginPage(driver, wait);
					loginPage.login(Username, Password);

					shoppingCartSummaryPage.DressesTab();
					shoppingCartSummaryPage.ClickListView();
					shoppingCartSummaryPage.AddProducts();
					shoppingCartSummaryPage.ContinueShopping();
					verifyText(By.xpath("//span[@class='heading-counter']"), "Your shopping cart contains: 5 Products");
					shoppingCartSummaryPage.VerifyTotalPriceOfEachItem();
					shoppingCartSummaryPage.AddPrice();

					if (!data("result").equals("WARN"))
						dataStream.put("result", "PASS");

				} catch (Exception e) {
					System.out.println("Need To define Test");

				} catch (Throwable throwable) {
					asyncThrowable[0] = throwable;

				} finally {
					synchronized (asyncExecuted) {
						asyncExecuted[0] = true;
						asyncExecuted.notify();
					}
				}
			}
		}).start();

		// Waiting for the test to complete

		synchronized (asyncExecuted) {
			while (!asyncExecuted[0]) {
				asyncExecuted.wait();
			}
		}

		// get any async error, including exceptions and assertationErrors

		if (asyncThrowable[0] != null) {
			throw asyncThrowable[0];
		}
	}

	public String data(String label) {
		return dataStream.get(label).toString();
	}

}
