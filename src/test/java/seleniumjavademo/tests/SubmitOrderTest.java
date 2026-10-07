package seleniumjavademo.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import seleniumjavademo.TestComponents.BaseTest;
import seleniumjavademo.data.DataReader;
import seleniumjavademo.pageobjects.CartPage;
import seleniumjavademo.pageobjects.CheckoutPage;
import seleniumjavademo.pageobjects.ConfirmationPage;
import seleniumjavademo.pageobjects.OrdersPage;
import seleniumjavademo.pageobjects.ProductCataloguePage;

public class SubmitOrderTest extends BaseTest {

	public String productName = "ZARA COAT 3";
	public String cvv = "4567";

	@Test(dataProvider = "getData", groups = "Purchase")
	public void submitOrder(HashMap<String, String> input) throws IOException {
		ProductCataloguePage cataloguePage = landingPage.loginToApp(input.get("email"), input.get("password"));
		cataloguePage.addProductToCart(input.get("product"));
		CartPage cartPage = cataloguePage.openCartPage();
		boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.fillCVVNumber(cvv);
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.clickPlaceOrderBtn();
		String orderSuccess = confirmationPage.getConfirmationMessage();

		Assert.assertEquals(orderSuccess, "THANKYOU FOR THE ORDER.");
//		Assert.assertTrue(orderSuccess.equalsIgnoreCase("Thankyou for the order."));
	}

	@Test
	public void orderHistoryTest() {
		ProductCataloguePage cataloguePage = landingPage.loginToApp("anuj@test.com", "Test@1234");
		cataloguePage.addProductToCart(productName);
		CartPage cartPage = cataloguePage.openCartPage();
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.fillCVVNumber(cvv);
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.clickPlaceOrderBtn();
		String orderSuccess = confirmationPage.getConfirmationMessage();
		Assert.assertEquals(orderSuccess, "THANKYOU FOR THE ORDER.");
		OrdersPage ordersPage = confirmationPage.openOrdersPage();

		Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "anuj@test.com");
//		map.put("password", "Test@1234");
//		map.put("product", "ZARA COAT 3");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map.put("email", "anuj1@test.com");
//		map.put("password", "Test@1234");
//		map.put("product", "ADIDAS ORIGINAL");
		DataReader reader = new DataReader();
		List<HashMap<String, String>> data = reader.getjSONData(
				System.getProperty("user.dir") + "\\src\\test\\java\\seleniumjavademo\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}
