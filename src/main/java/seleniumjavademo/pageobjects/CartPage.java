package seleniumjavademo.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cartSection h3")
	List<WebElement> cartProducts;

	@FindBy(xpath = "//*[@class='totalRow']/button")
	WebElement checkoutBtn;

	public boolean verifyProductDisplay(String productName) {
		boolean match = cartProducts.stream().anyMatch(prod -> prod.getText().equalsIgnoreCase(productName));
		return match;
	}

	public CheckoutPage goToCheckout() {
		checkoutBtn.click();
		return new CheckoutPage(driver);
	}

}
