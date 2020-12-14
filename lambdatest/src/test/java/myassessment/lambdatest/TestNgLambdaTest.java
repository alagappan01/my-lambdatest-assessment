package myassessment.lambdatest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import myassessment.lambdatestpages.FormPage;
import myassessment.lambdatestpages.LoginPage;

@Listeners(ListenerTest.class)

public class TestNgLambdaTest extends TestNgTestBase {

	WebDriver driver;
	LoginPage lp;
	FormPage fp;

	@BeforeClass
	public void setUp() {
		driver = getDriver();
		lp = new LoginPage(driver);
		fp = new FormPage(driver);
	}

	@Test(priority = 0)
	public void test_login_to_lambdatest_selenium_playground() throws InterruptedException {

		// Start by opening LambdaTest Selenium Playground
		lp.navigateToLoginPage();

		// Log in using the given credentials
		lp.setLoginPageUsername();
		lp.setLoginPagePassword();
		lp.clickLoginButton();

		WebElement toastMessageElement = lp.getLoginConfirmationToastMessageElement();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(toastMessageElement));

		// Assert if the Toast Message is displayed upon Successful Login
		AssertJUnit.assertTrue(toastMessageElement.getText().contains("Thank You Successully Login!!"));

		// Mark the login test Passed once the login success toast disappears
		// Wait until Toast Message hides after few seconds
		wait.until(ExpectedConditions.invisibilityOf(toastMessageElement));
		AssertJUnit.assertFalse(toastMessageElement.isDisplayed());

	}

	@Test(priority = 1)
	public void test_to_validate_ratingscale_slider_position() throws Exception {

		// Once you are on the form, fill in your registered email address in the first
		// field and click on populate
		fp.setFormPageEmailId();
		fp.clickFormPagePopulateButton();

		// Assert that the Email ID is displayed in the alert Box
		AssertJUnit.assertEquals(fp.getFormEmailId(), fp.getFormPageAlertText());

		Thread.sleep(2000);

		// Engage with the alert pop-up
		fp.handleFormPageAlertText();

		Thread.sleep(1000);
		fp.handleAcceptCookiesPolicy();

		// Answer the remaining questions on the feedback form engaging with
		// radiobuttons, checkboxes, and dropdown
		fp.setAnswerForQ1();
		Thread.sleep(500);
		fp.setAnswerForQ2();
		Thread.sleep(500);
		fp.setAnswerForQ3();

		Thread.sleep(500);
		// Enable the rating scale and feedback text field from the respective checkbox
		fp.setAnswerForQ4();

		Thread.sleep(500);
		// In the rating scale, set the ratings to 9 and assert if the selected position
		// of the slider is as required
		fp.setSliderPosition();
		AssertJUnit.assertTrue(fp.getSliderStyleValue().contains("88.88"));

		Thread.sleep(500);
		// Add some feedback comments in the Text Area
		fp.setFeedbackComment();

	}

	@Test(priority = 2)
	public void test_to_validate_image_upload_is_successful() throws Exception {

		Thread.sleep(500);
		// Open Selenium automation Page and Wait for all the elements load
		fp.openSeleniumAutomationPage();

		Thread.sleep(1000);
		fp.handleAcceptCookiesPolicy();

		Thread.sleep(500);
		// Find the Jenkins logo image that appears on the CI/CD tools integration
		// section and download the image file
		String snapshotName = fp.downloadImage();

		Thread.sleep(500);
		// Navigate back to the previous tab that has the Selenium playground open
		fp.goBackToSeleniumPlaygroundPage();

		Thread.sleep(500);
		// Upload the same image that you just downloaded from the automation page
		fp.formPageUploadImage(snapshotName);

		Thread.sleep(500);
		// Assert if the image is uploaded successfully
		String confirmationMessage = fp.assertUploadImageConfirmationMessage();
		Assert.assertEquals(confirmationMessage, "your image upload sucessfully!!");

	}

	@Test(priority = 3)
	public void test_to_validate_form_submission() throws Exception {

		driver = getDriver();
		FormPage fp = new FormPage(driver);

		Thread.sleep(500);
		// Click on submit the form and assert that the form gets submitted
		fp.submitForm();
		String confirmationMessage = fp.validateFormSubmission();
		Assert.assertTrue(confirmationMessage.contains("You have successfully submitted the form."));

	}

}
