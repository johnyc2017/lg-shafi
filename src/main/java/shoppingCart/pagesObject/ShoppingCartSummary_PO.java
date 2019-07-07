package shoppingCart.pagesObject;

import Utility_PO.FailTest;
import Utility_PO.Logger;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.TreeMap;

//import java.util.Locale;

public class ShoppingCartSummary_PO extends BasePage {

	TreeMap<String, String> dataStream;
	FailTest fail;
	ITestContext ctx;
	Logger testData;

	public ShoppingCartSummary_PO

	(WebDriver driver, WebDriverWait wait) {

	}

	Actions action = new Actions(driver);

	String DressesTab = "/html[1]/body[1]/div[1]/div[1]/header[1]/div[3]/div[1]/div[1]/div[6]/ul[1]/li[2]/a[1]";
	String ClickDress1 = ".//*[@id='block_top_menu']/ul/li[2]/a";
	//String AddToCart = "/html[1]/body[1]/div[1]/div[1]/div[3]/form[1]/div[1]/div[3]/div[1]/p[1]/button[1]/span[1]";
	String ContinueShopping = "//span[contains(text(),'Proceed to checkout')]";
	String ShoppingCartIcon = "//div[@class='shopping_cart']/a[1]";
	
	
	@Step("Click on Dresses Tab")
	public void DressesTab() {
		try {

			click(By.xpath(DressesTab));

		} catch (Exception e) {

			fail.failTest("Unable to click on Dresses Tab");
		}
	}

	@Step("Click on Dress 1")
	public void ClickDress1() {
		try {

			click(By.xpath(ClickDress1));

		} catch (Exception e) {

			fail.failTest("Unable to click on Dresses");
		}
	}
	

	@Step("Click to Continue Shopping")
	public void ContinueShopping() throws InterruptedException {
		try {
			click(By.xpath(ContinueShopping));
		} catch (Exception e) {

			fail.failTest("Unable to Continue Shopping");
		}
	}


	
	@Step("Click on Shopping Cart Icon")  
	public void AddProducts() {
	        WebElement element = driver.findElement(By.xpath("//span[@class='cross']"));
	        List<WebElement> lists = driver.findElements(By.xpath("//li//span[contains(text(),'Add to cart')]"));
	        for (WebElement item : lists) {
	            try {
	            	highlight(item);
	                item.click();
	                delayFor(1000);
	                element.click();
	            } catch (WebDriverException e) {

	            }
	        }
	  }
	
	@Step("Click on Shopping Cart Icon")
	public void ShoppingCartIcon() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("ShoppingCartIcon"));
		scrollToElement(element);
		try {
			click(By.xpath(ShoppingCartIcon));
		} catch (Exception e) {

			fail.failTest("Unable to Proceed to Checkout");
		}
	}


	@Step("Verify total price")
	public void AddPrice() {

		List<WebElement> lists = driver.findElements(By.xpath("//td[@class='cart_total']"));
		String product1 = lists.get(0).getText();
		String product2 = lists.get(1).getText();
		String product3 = lists.get(2).getText();
		String product4 = lists.get(3).getText();
		String product5 = lists.get(4).getText();

		String value1 = product1.substring(1);
		String value2 = product2.substring(1);
		String value3 = product3.substring(1);
		String value4 = product4.substring(1);
		String value5 = product5.substring(1);


		double total1 = Double.parseDouble(value1);
		double total2 = Double.parseDouble(value2);
		double total3 = Double.parseDouble(value3);
		double total4 = Double.parseDouble(value4);
		double total5 = Double.parseDouble(value5);

		double sum = total1+total2+total3+total4+total5;

		BigDecimal bd = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
		double newInput = bd.doubleValue();
		System.out.println(newInput);


		String totalProductPrice = driver.findElement(By.xpath("//td[@id='total_product']")).getText();

		String total = totalProductPrice.substring(1);

		double totalPrice = Double.parseDouble(total);
		System.out.println(totalPrice);

		Assert.assertEquals(newInput,totalPrice);

	}

	protected void highlight(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 2px solid red;");
			delayFor(200);
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");
			delayFor(200);
		}
	}

	public void delayFor(int timeInMili) {
		try {
			Thread.sleep(timeInMili);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ClickListView(){
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'List')]"));
		//scrollToElement(element);
		element.click();
	}

	public void VerifyTotalPriceOfEachItem(){
		verifyText(By.xpath("//tr[1]/td[6]/span[1]"),"$26.00");
		verifyText(By.xpath("//tr[2]/td[6]/span[1]"),"$50.99");
		verifyText(By.xpath("//tr[3]/td[6]/span[1]"),"$28.98");
		verifyText(By.xpath("//tr[4]/td[6]/span[1]"),"$30.50");
		verifyText(By.xpath("//tr[5]/td[6]/span[1]"),"$16.40");
	}
	
	public String data(String label) {
		return dataStream.get(label).toString();

	}
}
