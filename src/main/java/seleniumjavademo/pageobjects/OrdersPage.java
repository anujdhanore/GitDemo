package seleniumjavademo.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumjavademo.abstractcomponents.AbstractComponents;

public class OrdersPage extends AbstractComponents {

	WebDriver driver;

	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "tbody tr td:nth-child(3)")
	List<WebElement> productNames;

	public boolean verifyOrderDisplay(String productName) {
		waitForElementToAppear(By.tagName("h1"));
		boolean match = productNames.stream().anyMatch(prod -> prod.getText().equalsIgnoreCase(productName));
		return match;
	}
}
