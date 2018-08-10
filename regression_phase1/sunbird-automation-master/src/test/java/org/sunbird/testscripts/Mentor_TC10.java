/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/06/2018

* Purpose: Check for download button availability for a non org user.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreateMentorPage;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Mentor_TC10 extends BaseTest {
	
	CreatorUserPage createUserPage1=PageFactory.initElements(driver, CreatorUserPage.class);

	@Test
	public void CheckForDownloadButtonInMentor_TC10() throws Exception
	{
		
		//Step1: Login as Creator
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(MENTOR);

		
		//Step2: Navigate to profile and search for user and download button shouldn't be available
		
		List <TestDataForSunbird> objListOFTestDataForSunbird1=null;
		objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		CreatorUserPageObj cretorUserPageObj =new CreatorUserPageObj();
		cretorUserPageObj.navigateToProfileAndSearch(objListOFTestDataForSunbird1.get(8).getCourseName());
		GenericFunctions.waitWebDriver(1000);
		GenericFunctions generic = new GenericFunctions();
		CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
		generic.isElementPresent(createMentorPage.downloadButton);
		System.out.println("Download button was not available");
		
		

	}
}
