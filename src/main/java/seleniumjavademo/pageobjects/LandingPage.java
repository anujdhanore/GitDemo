package seleniumjavademo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumjavademo.abstractcomponents.AbstractComponents;

public class LandingPage extends AbstractComponents {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement userPassword;

	@FindBy(id = "login")
	WebElement loginBtn;

	@FindBy(css = ".toast-message")
	WebElement errorMsg;

	public ProductCataloguePage loginToApp(String username, String password) {
		userEmail.sendKeys(username);
		userPassword.sendKeys(password);
		loginBtn.click();
		return new ProductCataloguePage(driver);
	}

	public String getErrorMessage() {
		waitForElementToAppear(errorMsg);
		return errorMsg.getText();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}

}
