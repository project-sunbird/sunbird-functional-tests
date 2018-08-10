/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 06/28/2018

* Purpose: Open an Announcement,details should open up.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.pageobjects.CreatorAnnouncementPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Announcement_TC08 extends BaseTest {
	String announcementName;
	String verifyannouncementName;
	@Test
	public void openAnnouncement_TC08() throws Exception{
		
		// Step1: Login as Creator

		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(CREATOR);

		// Step2: Navigate to Announcement Dashboard in Dropdown

		CreatorAnnouncementPageObj creatorAnnouncementPageObj = new CreatorAnnouncementPageObj();
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();
		
		//Step3:  Click on particular Announcement.
		
		GenericFunctions.waitWebDriver(1000);
		CreatorAnnouncementPage createAnnouncementPage = PageFactory.initElements(driver,CreatorAnnouncementPage.class);
		announcementName = createAnnouncementPage.announcementTableNameContent.getText();
		createAnnouncementPage.announcementTableNameContent.click();
		System.out.println(announcementName);
		
		//Step4:  Accouncement details opens up.
		
		GenericFunctions.waitWebDriver(1000);
		verifyannouncementName = createAnnouncementPage.announcementReview.getText();
		AssertJUnit.assertEquals(announcementName, verifyannouncementName);
		System.out.println("Announcement is verified");
		
		
		
		
		
		
	}
	

}
