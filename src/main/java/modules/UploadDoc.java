package modules;

import static constants.Constants.COMMENT;
import static constants.Constants.FILE_NAME;
import static constants.Constants.WAIT_TIME;
import static utils.FileManagement.getFileAbsolutePath;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.PropertiesManager;

public class UploadDoc {

	WebDriver webDriver;
	String comment = PropertiesManager.getProperty(COMMENT);
	String fileName = PropertiesManager.getProperty(FILE_NAME);
	Integer waitTime = Integer.parseInt(PropertiesManager.getProperty(WAIT_TIME));

	@FindBy(how = How.XPATH, using = "//iframe")
	private WebElement iframe;

	@FindBy(how = How.XPATH, using = "//body")
	private WebElement iframeBody;

	@FindBy(how = How.ID, id = "mainContent_uploadTimesheetDoc_tglUpload_btnShowUpload")
	private WebElement expandUpload;

	@FindBy(how = How.XPATH, using = "//input[@type='file'][@class='ruFileInput']")
	private WebElement selectFile;

	@FindBy(how = How.ID, id = "ctl00_mainContent_uploadTimesheetDoc_tglUpload_txtDescription")
	private WebElement description;

	@FindBy(how = How.ID, id = "mainContent_uploadTimesheetDoc_tglUpload_btnUpload")
	private WebElement uploadFile;

	@FindBy(how = How.XPATH, using = "//span[@class='ruUploadProgress ruUploadSuccess']")
	private WebElement progressAndSuccess;

	@FindBy(how = How.ID, id = "mainContent_btnSubmit")
	private WebElement submitAll;

	public UploadDoc(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void fillComment() {
		System.out.println("Found " + iframe.getTagName());
		webDriver.switchTo().frame(iframe);
		iframeBody.sendKeys(comment);
		webDriver.switchTo().defaultContent();
	}

	public void upLoadTimeSheet() {
		if (expandUpload.isDisplayed()) {
			expandUpload.click();
			System.out.println("clicked on the expand upload");

			description.clear();
			description.sendKeys(comment);
			System.out.println("Filled description");

			selectFile.isEnabled();
			System.out.println("The file absolute path is" + System.lineSeparator() + getFileAbsolutePath(fileName));
			selectFile.sendKeys(getFileAbsolutePath(fileName));
			System.out.println("Select a file to upload");

			// Wait for file to upload
			WebDriverWait wait = new WebDriverWait(webDriver, waitTime);
			wait.until(ExpectedConditions.elementToBeClickable(progressAndSuccess));

			uploadFile.click();
			System.out.println("Clicked on upload file button");
		}
	}

	public void fillCommentAndUploadFile() {

		fillComment();

		System.out.println("Upload time sheet is not yet uplaod.");
		upLoadTimeSheet();

		// TODO only enable when it's time to submit timesheet
		// submitAll.click();

	}

}