/**

 * Created by Qualitrix Technologies Pvt Ltd.

 * @author: Abhinav kumar singh

 * Date: 06/26/2018

 * Purpose: Create Announcement as Creator and verify as Reciever.

 */
package org.sunbird.testscripts;


import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.List;

import org.openqa.selenium.WebElement;
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

public class Announcement_TC02 extends BaseTest {
	String announcementnameReview;
	String announcementName;
	boolean checkForDeleteButton=false;


	List <TestDataForSunbird> objListOFTestDataForSunbird= null;

	@Test
	public void createAnnouncement_TC02() throws Exception
	{
		
		CreatorAnnouncementPage createAnnouncementPage = PageFactory.initElements(driver,CreatorAnnouncementPage.class);
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		SignUpObj creatorLogin = new SignUpObj();
		CreatorAnnouncementPageObj creatorAnnouncementPageObj =new CreatorAnnouncementPageObj();
		
		//Step1: Login as Creator
		creatorLogin.userLogin(CREATOR);


		//Step2: Navigate to Announcement Dashboard in Dropdown

		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();

		//Step3:Create Announcement using description

		creatorAnnouncementPageObj.CreateAnnouncement();
		GenericFunctions.waitWebDriver(1500);
		String announcementNumber = GenericFunctions.readFromNotepad(".//announcementNumbers.txt").toString().toUpperCase();
		announcementName = objListOFTestDataForSunbird.get(7).getCourseName() + announcementNumber;

//		createAnnouncementPage.popupCloseIcon.click();
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(2000);
		
		//Step4: Logout as Creator
		creatorLogin.userLogout();
		 
		
		// Step5: Login as Reciever
		GenericFunctions.waitWebDriver(1500);
		creatorLogin.userLogin(REVIEWER);

		//Step6:check for the announcement

		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.seeAllAnnouncement.click();
		GenericFunctions.waitWebDriver(1500);

		announcementnameReview = createAnnouncementPage.reviewAnnouncementN.getText();
		AssertJUnit.assertEquals(announcementnameReview, announcementName);
		createAnnouncementPage.reviewAnnouncementN.click();
		GenericFunctions.waitWebDriver(500);
		createAnnouncementPage.popupCloseIcon.click();
		
		System.out.println("Created Announcement is available");

		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(2000);
		System.out.println("Test case 2 is completed");

		//Logout as Reciever
		creatorLogin.userLogout();




		//Test case 3 
		System.out.println("Execution of Test case 3 and 4");	

		//Login as creator
		creatorLogin.userLogin(CREATOR);


		//Step 2:Click on Announcement Dashboard.
		creatorAnnouncementPageObj.navigateToAnnouncementInDropDownMenu();

		//Step 3:Click on delete on particular Announcement.

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

		//Logout as Creator
		creatorLogin.userLogout();
		System.out.println("Execution of Test case 3 is completed");


		//Test case 4 
		System.out.println("Execution of Test case 4");

		//Login as Reciever
		creatorLogin.userLogin(REVIEWER);

		// step6: check that the same announcement deleted from their account also

		GenericFunctions.waitWebDriver(1000);
		createAnnouncementPage.seeAllAnnouncement.click();
		GenericFunctions.waitWebDriver(1500);
		for (WebElement ele : createAnnouncementPage.reviewAnnouncement) {
			announcementnameReview = ele.getText();
			Assert.assertNotEquals(announcementnameReview, announcementName);

		}
		System.out.println("Deleted Announcement is not available");

		//Logout as receiver
		creatorLogin.userLogout();
		System.out.println("Execution of Test case 4 is completed");


	}

}
