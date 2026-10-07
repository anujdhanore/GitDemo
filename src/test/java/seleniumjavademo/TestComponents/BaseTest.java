package seleniumjavademo.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import seleniumjavademo.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\seleniumjavademo\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless")) {
				options.addArguments("--headless=new");
			}
			driver = new ChromeDriver(options);
//			driver.manage().window().setSize(new Dimension(1440, 900));
		} else if (browserName.contains("edge")) {
			EdgeOptions options = new EdgeOptions();
			if (browserName.contains("headless")) {
				options.addArguments("--headless");
			}
			driver = new EdgeDriver(options);
		}

//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().setSize(new Dimension(1440, 900));
//		driver.manage().window().maximize();
		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	public String getScreenshot(String testCaseName) throws IOException {
		String filePath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(filePath);
		FileUtils.copyFile(source, file);
		return filePath;

	}

}
