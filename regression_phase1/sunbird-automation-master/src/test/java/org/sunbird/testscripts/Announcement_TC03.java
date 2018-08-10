/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 06/27/2018

* Purpose: Delete any particular Announcement.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.pageobjects.CreatorAnnouncementPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Announcement_TC03 extends BaseTest{
	
	String announcementnameReview;
	String announcementName;
	boolean checkForDeleteButton=false;
	
	@Test
	public void deleteAnnouncement_TC03() throws Exception
	{
		
			//Step1: Login as Creator
			
			SignUpObj creatorLogin = new SignUpObj();
			creatorLogin.userLogin(CREATOR);
			
			//Step2: Navigate to Announcement Dashboard in Dropdown
			
			CreatorAnnouncementPageObj creatorAnnouncementPageObj =new CreatorAnnouncementPageObj();
			creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();
			
			//Step3: click on Delete on particular Announcement

			CreatorAnnouncementPage createAnnouncementPage = PageFactory.initElements(driver,CreatorAnnouncementPage.class);
			GenericFunctions generic = new GenericFunctions();
			checkForDeleteButton = generic.isElementPresent(createAnnouncementPage.deleteAnnouncementButton);
			if (checkForDeleteButton==true) {
				announcementName = createAnnouncementPage.selectAnnouncementName.getText();
				System.out.println(announcementName+ " is present and deleting it");
				createAnnouncementPage.deleteAnnouncementButton.click();
			} else{
				createAnnouncementPage.nextarrowOnHomePage.click();
				GenericFunctions.waitWebDriver(1000);
				announcementName = createAnnouncementPage.selectAnnouncementName.getText();
				System.out.println(announcementName+" is not present for deleting");
				createAnnouncementPage.deleteAnnouncementButton.click();
			}
			GenericFunctions.waitWebDriver(1500);
			createAnnouncementPage.stopCreatingAnnouncementMessageYes.click();
			
			
			GenericFunctions.waitWebDriver(2000);
			
			//Logout as Creator
			creatorLogin.userLogout();
			
	}
	

}
