package shoppingCart.pagesObject;

import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;

import Utility_PO.FailTest;
import Utility_PO.Logger;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

	TreeMap<String, String> dataStream;
	FailTest fail;
	ITestContext ctx;
	Logger testData;

	public LoginPage

	(WebDriver driver, WebDriverWait wait) {

	}

	// Mutable Locator String
	String usenameId = "email";
	String passwordId = "passwd";
	String loginButtonId = "//p[@class='submit']//span[1]";
	String errorMessageUsernameXpath = "//li[contains(text(),'An email address required.')]";
	String errorMessagePasswordXpath = "//li[contains(text(),'Password is required.')]"; 																							

	// Chain Method Hierarchy

	@Step("Automation Practice Login Credential ")
	public void login(String username, String password) {

		try {

			writeText(By.id(usenameId), username);
			writeText(By.id(passwordId), password);
			click(By.xpath(loginButtonId));
			// Submit();

		} catch (Exception e) {

			fail.failTest("Unable to Login to Automation Practice");
		}
	}

	@Step("Verify username ")
	public void verifyLoginUserName(String expectedText) {
		try {

			Assert.assertEquals(readText(By.xpath(errorMessageUsernameXpath)), expectedText);

		} catch (Exception e) {

			fail.failTest("Unable to Assert Username");
		}
	}

	@Step("Verify Password ")
	public void verifyLoginPassword(String expectedText) {
		try {

			Assert.assertEquals(readText(By.xpath(errorMessagePasswordXpath)), expectedText);

		} catch (Exception e) {

			fail.failTest("Unable to Assert Password");
		}
	}

	public String data(String label) {
		return dataStream.get(label).toString();

	}
}
