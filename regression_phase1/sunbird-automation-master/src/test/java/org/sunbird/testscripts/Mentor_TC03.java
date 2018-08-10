/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/10/2018

* Purpose: Check for the invited member, course content should be available in home page.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Mentor_TC03 extends BaseTest 
{
	CreatorUserPage createUserPage=PageFactory.initElements(driver, CreatorUserPage.class);
	
	@Test
	public void checkAvailabilityCourseForInvitedMemberInMentor_TC03() throws Exception
	{
		
		
		//Step1: Login as mentor
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(MENTOR_S);
		
		
		//Step2: search for the course created by other user and create invite only batch
		
		CreateMentorPageObj createMentorPageObj =new CreateMentorPageObj();
		String courseName=createMentorPageObj.createInviteOnlyBatch();
		GenericFunctions.waitWebDriver(1000);
		
		
		//Step3: Logout as mentor
		
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(1000);
		creatorLogin.userLogout();
		
		
		//Step4: Login as invited member
		
		creatorLogin.userLogin(PUBLICUSER_S);
		
		
		//Step5: Search for the particular course and consume the course
		
		createMentorPageObj.navigateToCourseAndSearch(courseName);
		GenericFunctions.waitWebDriver(2000);
		
		//Step6: Logout as public user
		creatorLogin.userLogout();

		

	}
}
