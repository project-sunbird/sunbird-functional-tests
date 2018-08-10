/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 06/26/2018

* Purpose: Create Announcement as Creator ,verify as Receiver,delete the announcement as creator and verify at receiver end also.

*/
package org.sunbird.testscripts;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.sunbird.pageobjects.CreatorAnnouncementPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.generic.ReadTestDataFromExcel;
import java.util.List;
import org.sunbird.testdata.TestDataForSunbird;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreatorAnnouncementPage;
import org.sunbird.startup.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Announcement_TC02_03_04 extends BaseTest {
	String announcementnameReview;
	String announcementName;
	String announcementnameReview1;
	String announcementName1;
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	boolean checkForDeleteButton=true;
	
	@Test
	public void createAnnouncement_TC02() throws Exception
	{
		//Step1: Login as Creator
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(CREATOR);
		
		
		//Step2: Navigate to Announcement Dashboard in Dropdown
		
		CreatorAnnouncementPageObj creatorAnnouncementPageObj =new CreatorAnnouncementPageObj();
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();
		
		//Step3:Create Announcement using description
		
		creatorAnnouncementPageObj.CreateAnnouncement();
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		GenericFunctions.waitWebDriver(1500);
		String announcementNumber = GenericFunctions.readFromNotepad(".//announcementNumbers.txt").toString().toUpperCase();
		announcementName = objListOFTestDataForSunbird.get(7).getCourseName() + announcementNumber;
		
		// Step4: Logout as Creator
		
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(2000);
		creatorLogin.userLogout();

				
		// Step5: Login as Reciever
				
		GenericFunctions.waitWebDriver(1500);
		creatorLogin.userLogin(REVIEWER);
		
		
		
		//Step6:check for the announcement
		
		CreatorAnnouncementPage createAnnouncementPage = PageFactory.initElements(driver,CreatorAnnouncementPage.class);
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.seeAllAnnouncement.click();
		GenericFunctions.waitWebDriver(1500);
		
		announcementnameReview = createAnnouncementPage.reviewAnnouncementN.getText();
		System.out.println(announcementnameReview);
		AssertJUnit.assertEquals(announcementnameReview, announcementName);
		createAnnouncementPage.reviewAnnouncementN.click();
		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.popupCloseIcon.click();
		System.out.println("Created Announcement is available");
		
		// Step7: Logout as Reciever
		
		GenericFunctions.waitWebDriver(1500);
		creatorLogin.userLogout();
		
		//Step8: Login as Creator
		
		creatorLogin.userLogin(CREATOR);
		
		//Step9: Navigate to Announcement Dashboard in Dropdown
		
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();
		
		//Step10: click on Delete on particular Announcement
		
		GenericFunctions generic = new GenericFunctions();
		checkForDeleteButton = generic.isElementPresent(createAnnouncementPage.deleteAnnouncementButton);
		if (checkForDeleteButton==true) {
			announcementName1 = createAnnouncementPage.selectAnnouncementName.getText();
			System.out.println(announcementName1);
			createAnnouncementPage.deleteAnnouncementButton.click();
		} else{
			createAnnouncementPage.nextarrowOnHomePage.click();
			GenericFunctions.waitWebDriver(1000);
			announcementName1 = createAnnouncementPage.selectAnnouncementName.getText();
			System.out.println("Deleted Announcement is" +announcementName1);
			createAnnouncementPage.deleteAnnouncementButton.click();
		}
		GenericFunctions.waitWebDriver(1500);
		createAnnouncementPage.stopCreatingAnnouncementMessageYes.click();
		
		// Step11: Logout as Creator
		
		GenericFunctions.waitWebDriver(1500);
		creatorLogin.userLogout();
		
		// Step12: Login as Reviewer
		
		GenericFunctions.waitWebDriver(1500);
		creatorLogin.userLogin(REVIEWER);

		// step13: check that teh same announcement deleted from their account also

		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.seeAllAnnouncement.click();
		GenericFunctions.waitWebDriver(1500);
		for (WebElement ele : createAnnouncementPage.reviewAnnouncement) {
			announcementnameReview = ele.getText();
			Assert.assertNotEquals(announcementnameReview, announcementName1);

		}
		System.out.println("Deleted Announcement is not available");

	}

}
