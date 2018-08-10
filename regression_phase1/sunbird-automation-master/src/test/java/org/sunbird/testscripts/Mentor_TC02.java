/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/20/2018

* Purpose: Create course as Mentor and try to create open batch for it,public user should consume it,mentor can check the stats.

*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreateMentorPage;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Mentor_TC02 extends BaseTest
{
	@Test
	public void consumeOpenCourseByPublicUserInMentor_TC02() throws Exception
	{
		CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		SignUpObj userLogin = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		CreateMentorPageObj createMentorPageObj=new CreateMentorPageObj();
	
		
		
		//Step1: Login as Mentor
		
		userLogin.userLogin(MENTORS_S);
		
		
		//Step2: Navigate to WorkSpace
		
		creatorUserPageObj.navigateToWorkspace(COURSE);
		
		
		//Step3: Create new Course
		
		creatorUserPageObj.createCourse(objListOFTestDataForSunbird);
		
		
		//Step4: Save and Send for Review
		
		creatorUserPageObj.saveAndSendCourseForReview(objListOFTestDataForSunbird);
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
	
		
		//Step5: Check for course in review submissions 
		creatorUserPageObj.reviewInSubmissions(COURSE,objListOFTestDataForSunbird);
		GenericFunctions.waitWebDriver(3000);

		
		//Step6: Logout as Mentor
		
		userLogin.userLogout();
		GenericFunctions.waitWebDriver(3000);
		
		
		//Step7: Login as Reviewer
		
		userLogin.userLogin(REVIEWER_S);
		
		
		//Step8: Search the course which was submitted for review
		
		GenericFunctions.waitWebDriver(2000);
		creatorUserPageObj.searchInUpForReview(COURSE,objListOFTestDataForSunbird);
		
		
		//Step9:Publish the Course
		
		String courseName=creatorUserPageObj.publishAndSearch(COURSE,objListOFTestDataForSunbird);
		System.out.println(courseName);
		
		//Step10: Logout as Reviewer		
		
		userLogin.userLogout();	
		
		
		//Step11:Login as Mentor
		
		userLogin.userLogin(MENTORS_S);
		
		
		//Step12:Search the course created by same user
		
		createMentorPageObj.navigateToCourseAndSearchForOpenBatch(courseName);
		
		
		//Sttep13:create invite only batch
		
		createMentorPageObj.createOpenBatch();
		
		
		//Step14:Logout as Mentor
		
		userLogin.userLogout();	
		GenericFunctions.waitWebDriver(3000);
		
		
		//Step15:Login as Public user
		
		userLogin.userLogin(PUBLICUSERS_S);
		
		
		//Step16:Search for the course
		
		createMentorPageObj.navigateToCourseAndSearchForOpenBatch(courseName);
		
		
		//Step17:Enroll for the open batch
		
		createMentorPageObj.enrollForOpenBatch();
		GenericFunctions.waitWebDriver(3000);
		
		
		
		//Step18:Logout as public user
		
		userLogin.userLogout();	
		GenericFunctions.waitWebDriver(2000);
		
		
		//Step19:Login as Mentor
		
		userLogin.userLogin(MENTORS_S);
		
		
		//Step20:Search the course and check the stats
		
		
		createMentorPageObj.navigateToCourseAndSearchForOpenBatch(courseName);
		GenericFunctions.waitWebDriver(2500);
		createMentorPageObj.viewCourseStats();
		//createMentorPage.viewCourseStat.click();
		
		
		
		
	}
	
	
}
