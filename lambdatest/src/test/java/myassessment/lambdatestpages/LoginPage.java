package myassessment.lambdatestpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends PropertiesFileRead {

	WebDriver driver;
	String appPropertyFilePath;

	By usernameTextbox = By.id("username");
	By passWordTextbox = By.id("password");
	By loginButton = By.className("applynow");
	By loginToastMsgbox = By.xpath("//*[@class=\"toast jam\"]");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.appPropertyFilePath = getAppPropertyFilePath();
	}

	public String getLoginPageUrl() {

		String loginpage_url = getProperty(appPropertyFilePath, "loginpage.url");

		if (loginpage_url != null)
			return loginpage_url;

		else
			throw new RuntimeException("LoginPage Url not specified in the Properties file.");

	}

	public String getLoginPageUsername() {

		String loginpage_username = getProperty(appPropertyFilePath, "loginpage.username");

		if (loginpage_username != null)
			return loginpage_username;

		else
			throw new RuntimeException("LoginPage Url not specified in the Properties file.");

	}

	public String getLoginPagePassword() {

		String loginpage_password = getProperty(appPropertyFilePath, "loginpage.password");

		if (loginpage_password != null)
			return loginpage_password;

		else
			throw new RuntimeException("LoginPage Url not specified in the Properties file.");

	}

	public void navigateToLoginPage() {
		driver.get(this.getLoginPageUrl());
	}

	public void setLoginPageUsername() {
		driver.findElement(usernameTextbox).sendKeys(this.getLoginPageUsername());
	}

	public void setLoginPagePassword() {
		driver.findElement(passWordTextbox).sendKeys(this.getLoginPagePassword());
	}

	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}

	public WebElement getLoginConfirmationToastMessageElement() {
		return driver.findElement(loginToastMsgbox);
	}

}
