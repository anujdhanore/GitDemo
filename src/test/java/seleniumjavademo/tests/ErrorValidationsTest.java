package seleniumjavademo.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumjavademo.TestComponents.BaseTest;
import seleniumjavademo.TestComponents.Retry;
import seleniumjavademo.pageobjects.CartPage;
import seleniumjavademo.pageobjects.ProductCataloguePage;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = { "Error Handling" }, retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException {
		landingPage.loginToApp("anuj@test.com", "Test@123");
		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
	}

	@Test
	public void productErrorValidation() {
		String productName = "ZARA COAT 3";
		ProductCataloguePage cataloguePage = landingPage.loginToApp("anuj1@test.com", "Test@1234");
		cataloguePage.addProductToCart(productName);
		CartPage cartPage = cataloguePage.openCartPage();
		boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);

	}

}
