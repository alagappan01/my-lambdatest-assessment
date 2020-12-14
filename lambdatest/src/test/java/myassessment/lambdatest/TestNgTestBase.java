package myassessment.lambdatest;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import myassessment.lambdatestpages.PropertiesFileRead;

public class TestNgTestBase {

	WebDriver driverNew;
	PropertiesFileRead pfr = new PropertiesFileRead();

	@Parameters({ "environment", "browserName", "browserVersion", "platformName" })
	@BeforeClass
	public void setUp(String environment, String browserName, String browserVersion, String platformName)
			throws MalformedURLException {
		pfr.getRemoteWebDriver(environment, browserName, browserVersion, platformName);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@BeforeClass
	public WebDriver getDriver() {
		this.driverNew=pfr.getDriver();
		return this.driverNew;
	}

	@AfterClass
	public void tearDown() {
		if (getDriver() != null)
			getDriver().quit();
	}

}
