/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/11/2018

* Purpose: After creating invite only batch for a course,check invited member can use it ,then check course stats as mentor.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.page.CreateMentorPage;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Mentor_TC01 extends BaseTest 
{
	CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
	CreatorUserPage createUserPage=PageFactory.initElements(driver, CreatorUserPage.class);
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	
	@Test
	public void checkCourseStatAsMentor_TC01() throws Exception
	{
		
		
		//Step1: Login as mentor
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(MENTOR_S);
		
		
		//Step2: Search the course created by other user and create a invite only batch	
		CreateMentorPageObj createMentorPageObj =new CreateMentorPageObj();
		String courseName=createMentorPageObj.createInviteOnlyBatch();
		GenericFunctions.waitWebDriver(1000);
		
		
		//Step3: Logout as mentor
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(1000);
		creatorLogin.userLogout();
		
		
		//Step4: Login as invited member.
		creatorLogin.userLogin(PUBLICUSER_S);
		GenericFunctions.waitWebDriver(3000);
				
				
		//Step5: Search for the course.
		createMentorPageObj.navigateToCourseAndSearch(courseName);
		//createMentorPage.resumeCourse.click();
				
			
		//Step6: Logout as invited member
		GenericFunctions.waitWebDriver(6000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(1000);
		creatorLogin.userLogout();
				
				
		//Step7: Login as mentor
		creatorLogin.userLogin(MENTORS_S);
				
				
		//Step8: check for course stats 
		createMentorPageObj.navigateToCourseAndSearch(courseName);
		//GenericFunctions.waitForElementToAppear(createMentorPage.viewCourseStat);
		GenericFunctions.waitWebDriver(2500);
		createMentorPageObj.viewCourseStats();
		
		/*createMentorPage.viewCourseStat.click();
		GenericFunctions.waitWebDriver(2000);*/
		
		//Logout as mentor
		creatorLogin.userLogout();
		
	}
}