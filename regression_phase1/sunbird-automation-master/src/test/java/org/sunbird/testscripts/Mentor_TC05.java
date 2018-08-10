/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/16/2018

* Purpose: Create course as Mentor and try to create open batch for it,public user should consume it,mentor can check the stats.

*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Mentor_TC05 extends BaseTest
{
	@Test
	public void checkStatForOpenBatchMentor_TC05() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForsunbird= null ;
		objListOFTestDataForsunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		SignUpObj userLogin = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		CreateMentorPageObj createMentorPageObj=new CreateMentorPageObj();
		
		//Step1: Login as Mentor
		userLogin.userLogin(MENTOR_S);
		
		//Step2: Navigate to WorkSpace
		creatorUserPageObj.navigateToWorkspace(COURSE);
		
		//Step3: Create new Course
		creatorUserPageObj.createCourse(objListOFTestDataForsunbird);
		
		//Step4: Save and Send for Review
		creatorUserPageObj.saveAndSendCourseForReview(objListOFTestDataForsunbird);
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
	
		//Step5: Check for course in review submissions 
		creatorUserPageObj.reviewInSubmissions(COURSE,objListOFTestDataForsunbird);
		GenericFunctions.waitWebDriver(3000);

		//Step6: Logout as Creator
		userLogin.userLogout();
		
		GenericFunctions.waitWebDriver(2000);
		//Step7: Login as Reviewer
		userLogin.userLogin(REVIEWER_S);
		
		
		//Step8: Search the course which was submitted for review
		
		GenericFunctions.waitWebDriver(2000);
		creatorUserPageObj.searchInUpForReview(COURSE,objListOFTestDataForsunbird);
		
		
		//Publish the Course
		
		creatorUserPageObj.publishAndSearch(COURSE,objListOFTestDataForsunbird);
		
		
		//Step9: Logout as Reviewer	
		
		userLogin.userLogout();	
		
		
		//Login as Mentor
		
		userLogin.userLogin(MENTOR_S);
		
		
		//Search the course 
		
		createMentorPageObj.createInviteOnlyBatch();
	}
	
	
}
