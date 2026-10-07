package seleniumjavademo.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import seleniumjavademo.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		String productName = "ZARA COAT 3";
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client/");
		LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("anuj@test.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test@1234");
		driver.findElement(By.id("login")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".card-body button:last-of-type")));
		List<WebElement> products = driver.findElements(By.cssSelector("div.card"));

		products.stream().forEach(s -> System.out.println(s.getText()));

		WebElement product = products.stream()
				.filter(prod -> prod.findElement(By.tagName("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		product.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(prod -> prod.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);

		driver.findElement(By.xpath("//*[@class='totalRow']/button")).click();

		driver.findElement(By.xpath("//div[@class='form__cc']//div[contains(text(),'CVV')]/following-sibling::input"))
				.sendKeys("4567");

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		// driver.findElement(By.cssSelector("[placeholder='Select
		// Country']")).sendKeys("india");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.ta-results.list-group")));
		List<WebElement> countryNames = driver.findElements(By.cssSelector("button.ta-item.list-group-item"));
		countryNames.stream().filter(country -> country.getText().equalsIgnoreCase("India")).findFirst().orElseThrow()
				.click();
		driver.findElement(By.cssSelector(".btnn.action__submit")).click();
		String orderSuccess = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(orderSuccess.equalsIgnoreCase("Thankyou for the order."));
	}

}