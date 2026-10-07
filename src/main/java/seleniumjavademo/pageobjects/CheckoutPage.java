package seleniumjavademo.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumjavademo.abstractcomponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents {

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='form__cc']//div[contains(text(),'CVV')]/following-sibling::input")
	WebElement cvvField;

	@FindBy(css = "[placeholder='Select Country']")
	WebElement selectCountryBox;

	@FindBy(css = "button.ta-item.list-group-item")
	List<WebElement> countryAutoCompleteList;

	@FindBy(css = ".btnn.action__submit")
	WebElement PlaceOrderBtn;

	By countryAutoCompleteBox = By.cssSelector("section.ta-results.list-group");

	public void fillCVVNumber(String cvv) {
		cvvField.sendKeys(cvv);
	}

	public void selectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(selectCountryBox, countryName).build().perform();
		waitForElementToAppear(countryAutoCompleteBox);
		countryAutoCompleteList.stream().filter(country -> country.getText().equalsIgnoreCase("India")).findFirst()
				.orElseThrow().click();

//driver.findElement(By.cssSelector(".btnn.action__submit")).click();
	}

	public ConfirmationPage clickPlaceOrderBtn() {
		PlaceOrderBtn.click();
		return new ConfirmationPage(driver);
	}

}
