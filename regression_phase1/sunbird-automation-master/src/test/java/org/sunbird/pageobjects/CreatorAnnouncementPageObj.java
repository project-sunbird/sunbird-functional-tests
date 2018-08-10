package org.sunbird.pageobjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.page.PublicUserPage;
import org.sunbird.page.SignUp;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;


public class CreatorAnnouncementPageObj extends BaseTest{
	
	//private static final String IMAGE = null;
	WebDriverWait wait = new WebDriverWait(driver,20);
	CreatorUserPage createUserPage=PageFactory.initElements(driver, CreatorUserPage.class);
	CreatorAnnouncementPage createAnnouncementPage=PageFactory.initElements(driver, CreatorAnnouncementPage.class);
	PublicUserPage publicUserPage = PageFactory.initElements(driver, PublicUserPage.class);
	SignUp signUpPage=PageFactory.initElements(driver, SignUp.class);
	static Logger log = Logger.getLogger(CreatorAnnouncementPage.class.getName());
	Actions action = new Actions(driver);
	Random rand=new Random();
	String announcementName;
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	
	
	
	public void navigateToAnnouncementInDropDownMenu() throws InterruptedException
	{
		try{
			GenericFunctions.waitWebDriver(1500);
			createUserPage.dropDown.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.announcement_Dashboard.click();	
		}
		catch(Exception e){
			log.error("Exception In the method navigateToAnnouncementInDropDownMenu"+e.getMessage());
		}
	}
	
	public void CreateAnnouncement() throws InterruptedException, Exception
	{
		
		
			Robot robot = new Robot();
			objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
			GenericFunctions.waitWebDriver(1500);
			createAnnouncementPage.create_Announcement.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.enterTitle.click();
			String announcementNumber = GenericFunctions.testDataIncrementer("./announcementNumbers.txt").toString();
			announcementName = objListOFTestDataForSunbird.get(7).getCourseName();
			System.out.println(announcementName + announcementNumber);
			createAnnouncementPage.enterTitle.sendKeys(announcementName + announcementNumber );
			createAnnouncementPage.enterOrg.click();
			createAnnouncementPage.enterOrg.sendKeys(objListOFTestDataForSunbird.get(7).getCourseDescription());
			createAnnouncementPage.announcementType.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.announcementTypeOrder.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.enterDescription.click();
			createAnnouncementPage.enterDescription.sendKeys(objListOFTestDataForSunbird.get(7).getTitle());
			GenericFunctions.waitWebDriver(2000);
			createAnnouncementPage.addUrl.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.enterUrl.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.enterUrl.sendKeys(objListOFTestDataForSunbird.get(7).getTitleDescription());
			GenericFunctions.waitWebDriver(1000);
			for(int i=1;i<=3;i++)
			{
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			}
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			GenericFunctions.waitWebDriver(2000);
			System.out.println("Button clicked");
			GenericFunctions.waitWebDriver(2000);
			createAnnouncementPage.searchForLocation.click();
			GenericFunctions.waitWebDriver(2000);
			createAnnouncementPage.searchForLocation.sendKeys("vizianagaram");
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.checkLocation.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.confirmRecipients.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.previewAnnouncement.click();
			GenericFunctions.waitWebDriver(1000);
			createAnnouncementPage.sendAnnouncement.click();
			createAnnouncementPage.announcementcreatedConfirmation.click();
	}
	
	public void CreateAnnouncementUsingAttachment() throws InterruptedException, Exception
	{
		
		Robot robot = new Robot();
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitWebDriver(1500);
		createAnnouncementPage.create_Announcement.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.enterTitle.click();
		String announcementNumber = GenericFunctions.testDataIncrementer("./announcementNumbers.txt").toString();
		announcementName = objListOFTestDataForSunbird.get(7).getCourseName();
		System.out.println(announcementName + announcementNumber);
		createAnnouncementPage.enterTitle.sendKeys(announcementName + announcementNumber );
		createAnnouncementPage.enterOrg.click();
		createAnnouncementPage.enterOrg.sendKeys(objListOFTestDataForSunbird.get(7).getCourseDescription());
		createAnnouncementPage.announcementType.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.announcementTypeOrder.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.enterDescription.click();
		createAnnouncementPage.enterDescription.sendKeys(objListOFTestDataForSunbird.get(7).getTitle());
		GenericFunctions.waitWebDriver(2000);
		createAnnouncementPage.addUrl.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.enterUrl.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.enterUrl.sendKeys(objListOFTestDataForSunbird.get(7).getTitleDescription());
		//GenericFunctions.waitWebDriver(1000);
		GenericFunctions.waitWebDriver(2000);
		for(int i=1;i<=2;i++)
		{
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		GenericFunctions.waitWebDriver(2000);
		String path = System.getProperty("user.dir")+"/UploadingDocuments/Upload Document Contents/"+IMAGE;
		GenericFunctions.uploadFile(path);
		GenericFunctions.waitWebDriver(5000);
		//GenericFunctions.waitWebDriver(2000);
		for(int i=1;i<=2;i++)
		{
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		GenericFunctions.waitWebDriver(4000);
		System.out.println("Button clicked");
		GenericFunctions.waitWebDriver(3000);
		createAnnouncementPage.searchForLocation.click();
		GenericFunctions.waitWebDriver(2000);
		createAnnouncementPage.searchForLocation.sendKeys("vizianagaram");
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.checkLocation.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.confirmRecipients.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.previewAnnouncement.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.sendAnnouncement.click();
		createAnnouncementPage.announcementcreatedConfirmation.click();
	}

}
