package seleniumjavademo.stepDefinition;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import seleniumjavademo.TestComponents.BaseTest;
import seleniumjavademo.pageobjects.CartPage;
import seleniumjavademo.pageobjects.CheckoutPage;
import seleniumjavademo.pageobjects.ConfirmationPage;
import seleniumjavademo.pageobjects.LandingPage;
import seleniumjavademo.pageobjects.ProductCataloguePage;

public class StepDefinitionImpl extends BaseTest {

	public LandingPage landingPage;
	public ProductCataloguePage cataloguePage;
	public ConfirmationPage confirmationPage;

	@Given("I landed on Ecommerce Page")
	public void i_landed_on_ecommerce_page() throws IOException {
		landingPage = launchApplication();
	}

	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password) {
		cataloguePage = landingPage.loginToApp(username, password);
	}

	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) {
		cataloguePage.addProductToCart(productName);
	}

	@When("^Checkout (.+) and submit the order$")
	public void checkout_product_and_submit_the_order(String productName) {
		CartPage cartPage = cataloguePage.openCartPage();
		boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.fillCVVNumber("4567");
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.clickPlaceOrderBtn();
	}

	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_on_confirmationpage(String string) {
		String orderSuccess = confirmationPage.getConfirmationMessage();
		Assert.assertEquals(orderSuccess, string);
		driver.quit();
	}

	@Then("^\"([^\"]*)\" message is displayed$")
	public void something_message_is_displayed(String strArg1) throws Throwable {

		Assert.assertEquals(strArg1, landingPage.getErrorMessage());
		driver.quit();
	}
}
