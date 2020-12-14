package myassessment.lambdatestpages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormPage extends PropertiesFileRead {

	WebDriver driver;
	String appPropertyFilePath;

	By emailTextbox = By.id("developer-name");
	By populateButton = By.id("populate");
	By q1RadioButton = By.id("6months");
	By q2CheckBox1 = By.name("discounts");
	By q2CheckBox2 = By.name("delivery-time");
	By q3Dropdown = By.id("preferred-payment");
	By q4Checkbox = By.name("tried-ecom");
	By slider = By.xpath("//*[@id=\"slider\"]/span");
	By commentsTextArea = By.id("comments");
	By cookiesAccept = By.xpath("//*[@class=\"cookiesdiv\"]//*[contains(text(),\"ACCEPT\")]");
	By ciToolBox = By.className("citoolbox");
	By jenkinsImage = By.xpath("//*[@class=\"citoolbox\"]//img[contains(@src,\"jenkins\")]");
	By uploadImagebutton = By.id("img");
	By uploadImageFile = By.id("file");
	By submitButton = By.id("submit-button");
	By formSubmitConfirmationMessage = By.id("message");

	public FormPage(WebDriver driver) {
		this.driver = driver;
		this.appPropertyFilePath = getAppPropertyFilePath();
	}

	public String getFormEmailId() {

		String formpage_emailid = getProperty(appPropertyFilePath, "formpage.emailid");

		if (formpage_emailid != null)
			return formpage_emailid;

		else
			throw new RuntimeException("Formpage EmailID not specified in the Properties file.");

	}

	public void setFormPageEmailId() {
		driver.findElement(emailTextbox).sendKeys(this.getFormEmailId());
	}

	public void clickFormPagePopulateButton() {
		driver.findElement(populateButton).click();
	}

	public String getFormPageAlertText() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

	public void handleFormPageAlertText() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public void handleAcceptCookiesPolicy() {
		if (driver.findElement(cookiesAccept).isDisplayed())
			driver.findElement(cookiesAccept).click();
	}

	public void setAnswerForQ1() {
		WebElement element = driver.findElement(q1RadioButton);
		element.click();
	}

	public void setAnswerForQ2() {
		WebElement element = driver.findElement(q2CheckBox1);
		this.scrollUntilElement(element);
		element.click();

		WebElement element1 = driver.findElement(q2CheckBox2);
		this.scrollUntilElement(element1);
		element1.click();
	}

	public void setAnswerForQ3() {
		WebElement element = driver.findElement(q3Dropdown);
		this.scrollUntilElement(element);

		Select select = new Select(element);
		select.selectByVisibleText("Cash on delivery");
	}

	public void setAnswerForQ4() {
		WebElement element = driver.findElement(q4Checkbox);
		this.scrollUntilElement(element);

		element.click();
	}

	public void setSliderPosition() {
		WebElement element = driver.findElement(slider);
		this.scrollUntilElement(element);

		element.click();

		for (int i = 1; i <= 8; i++) {
			element.sendKeys(Keys.ARROW_RIGHT);
		}
	}

	public String getSliderStyleValue() {
		return driver.findElement(slider).getAttribute("style");
	}

	public void setFeedbackComment() {
		WebElement element = driver.findElement(commentsTextArea);
		this.scrollUntilElement(element);

		element.sendKeys("Hello, this is test feedback comment only");
	}

	public void scrollUntilElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while (ExpectedConditions.elementToBeClickable(element) == null)
			js.executeScript("window.scrollBy(0,50)", "");

	}

	public void openSeleniumAutomationPage() {
		String seleniumAutomationPage = getProperty(appPropertyFilePath, "seleniumautomationpage.url");

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		driver.switchTo().window(tabs.get(1));
		driver.get(seleniumAutomationPage);

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(expectation);
	}

	public String downloadImage() throws Exception {

		Thread.sleep(2000);
		WebElement element = driver.findElement(ciToolBox);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(2000);

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File fileName = new File("jenkins.jpeg");

		File screen = scrShot.getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(screen, fileName);

		return fileName.getAbsolutePath();

	}

	public void goBackToSeleniumPlaygroundPage() throws InterruptedException {
		Thread.sleep(2000);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(uploadImagebutton));
	}

	public void formPageUploadImage(String srcImagePath) throws IOException, InterruptedException {

		Thread.sleep(2000);
		String destImagePath = "jenkins.svg";

		File srcImageFile = new File(srcImagePath);
		File destImageFile = new File(destImagePath);

		FileUtils.copyFile(srcImageFile, destImageFile);

		driver.findElement(uploadImageFile).sendKeys(destImageFile.getAbsolutePath());

	}

	public String assertUploadImageConfirmationMessage() throws InterruptedException {
		Thread.sleep(1000);
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	public void submitForm() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(submitButton).click();
	}

	public String validateFormSubmission() throws InterruptedException {
		Thread.sleep(1000);
		String innerText = driver.findElement(formSubmitConfirmationMessage).getText();
		return innerText;
	}

}
