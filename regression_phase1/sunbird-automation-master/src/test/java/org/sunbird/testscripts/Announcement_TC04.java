/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 06/28/2018

* Purpose: Delete the announcement and verify as reviewer it is deleted or not.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.pageobjects.CreatorAnnouncementPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Announcement_TC04 extends BaseTest {
	String announcementnameReview;
	String announcementName;
	boolean checkForDeleteButton=false;

	@Test
	public void deleteAndVerifyAnnouncement_TC04() throws Exception {

		// Step1: Login as Creator

		SignUpObj creatorLogin = new SignUpObj();
		CreatorAnnouncementPageObj creatorAnnouncementPageObj = new CreatorAnnouncementPageObj();
		creatorLogin.userLogin(CREATOR);

		// Step2: Navigate to Announcement Dashboard in Dropdown
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();

		// Step3: click on Delete on particular Announcement
		CreatorAnnouncementPage createAnnouncementPage = PageFactory.initElements(driver,CreatorAnnouncementPage.class);
		GenericFunctions generic = new GenericFunctions();
		checkForDeleteButton = generic.isElementPresent(createAnnouncementPage.deleteAnnouncementButton);
		if (checkForDeleteButton==true) {
			announcementName = createAnnouncementPage.selectAnnouncementName.getText();
			System.out.println(announcementName);
			createAnnouncementPage.deleteAnnouncementButton.click();
		} else{
			createAnnouncementPage.nextarrowOnHomePage.click();
			GenericFunctions.waitWebDriver(1000);
			announcementName = createAnnouncementPage.selectAnnouncementName.getText();
			System.out.println(announcementName);
			createAnnouncementPage.deleteAnnouncementButton.click();
		}
		GenericFunctions.waitWebDriver(1500);
		createAnnouncementPage.stopCreatingAnnouncementMessageYes.click();

		
		// Step4: Logout as Creator
		
		GenericFunctions.waitWebDriver(1000);
		GenericFunctions.refreshWebPage();
		creatorLogin.userLogout();

		
		// Step5: Login as Reviewer
		
		GenericFunctions.waitWebDriver(1500);
		creatorLogin.userLogin(REVIEWER);

		// step6: Review the Announcement.use Assert
		
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.seeAllAnnouncement.click();
		GenericFunctions.waitWebDriver(1500);
		for (WebElement ele : createAnnouncementPage.reviewAnnouncement) {
			announcementnameReview = ele.getText();
			Assert.assertNotEquals(announcementnameReview, announcementName);

		}
		System.out.println("Deleted Announcement is not available");

	}
	

	

}
