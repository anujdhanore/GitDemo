package seleniumjavademo.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumjavademo.abstractcomponents.AbstractComponents;

public class ProductCataloguePage extends AbstractComponents{

	WebDriver driver;

	public ProductCataloguePage(WebDriver driver) {
		
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// List<WebElement> products = driver.findElements(By.cssSelector("div.card"));

	// PageFactory
	@FindBy(css = "div.card")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement loader;
	
	By addToCartBtnVisible = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.id("toast-container");
	// 
	
	public List<WebElement> getProductList() {
		
		waitForElementToAppear(addToCartBtnVisible);
		return products;
	}
	
	public WebElement getProductByName(String productName) {
		
		WebElement product = getProductList().stream()
				.filter(prod -> prod.findElement(By.tagName("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		return product;
	}
	
	public void addProductToCart(String productName) {
		
		WebElement product = getProductByName(productName);
		product.findElement(addToCartBtnVisible).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(loader);
	}

}
