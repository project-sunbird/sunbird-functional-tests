/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 06/26/2018

* Purpose: Resend and edit the Announcement and verify announcement sender receives it.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.pageobjects.CreatorAnnouncementPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Announcement_TC05 extends BaseTest{
	String announcementnameReview;
	String announcementName;
	boolean checkForElement=false;
	boolean checkForResendButton=false;
	String editTitle;
	String edittedTitle;
	int count=7;
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	@Test
	public void resendAnnouncement_TC05() throws InterruptedException, Exception
	{
		//1.Login as creator.
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(CREATOR);
		
		//2.Click on Announcement Dashboard.
		
		CreatorAnnouncementPageObj creatorAnnouncementPageObj =new CreatorAnnouncementPageObj();
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();
		
		//3.Click on resend.
		
		CreatorAnnouncementPage createAnnouncementPage = PageFactory.initElements(driver,CreatorAnnouncementPage.class);
		GenericFunctions generic = new GenericFunctions();
		checkForResendButton = generic.isElementPresent(createAnnouncementPage.resendAnnouncementButton);
		if (checkForResendButton==true) {
			announcementName = createAnnouncementPage.resendAnnouncementName.getText();
			System.out.println(announcementName);
			createAnnouncementPage.resendAnnouncementButton.click();
		} else{
			createAnnouncementPage.nextarrowOnHomePage.click();
			GenericFunctions.waitWebDriver(1000);
			announcementName = createAnnouncementPage.resendAnnouncementName.getText();
			System.out.println(announcementName);
			createAnnouncementPage.resendAnnouncementButton.click();
		}
		GenericFunctions.waitWebDriver(1500);
		
		
		//4.Edit the details.
		
		Robot robot = new Robot();
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitWebDriver(1500);
		createAnnouncementPage.enterTitle.click();
		createAnnouncementPage.enterTitle.clear();
		//String announcementNumber = GenericFunctions.readFromNotepad(".//announcementNumbers.txt").toString().toUpperCase();
		editTitle = objListOFTestDataForSunbird.get(7).getCourseName(); // + announcementNumber;
		
		GenericFunctions.waitWebDriver(1000);
		
		
		for(int i=1;i<=count;i++)
		{
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		GenericFunctions.waitWebDriver(2000);

		//6.Select location.
		
		createAnnouncementPage.searchForLocation.click();
		GenericFunctions.waitWebDriver(2000);
		
		
		//7.Click on confirm recipients.
		
		createAnnouncementPage.confirmRecipients.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.previewAnnouncement.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.sendAnnouncement.click();
		createAnnouncementPage.announcementcreatedConfirmation.click();
		
		
		
		//8. Check that the announcement sender receives the announcement
		
		edittedTitle = createAnnouncementPage.resendAnnouncementName.getText();
		System.out.println(edittedTitle);
		AssertJUnit.assertEquals(editTitle, edittedTitle);
		System.out.println("updated info verified");
	}

}
