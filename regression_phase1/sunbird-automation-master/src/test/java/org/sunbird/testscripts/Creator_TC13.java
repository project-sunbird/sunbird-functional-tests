/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/22/2018
* Purpose: Edit and save a Reject content and send for review
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Creator_TC13 extends BaseTest
{
	@Test(priority=13, groups={"Creator Group"})
	public void editAndSendForReviewTestCase13() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");

		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1:Login as content creator
		signupObj.userLogin(CREATOR);

		//Step 2:Navigate to WorkSpace
		creatorUserPageObj.navigateToWorkspace(COURSE);

		//Step 3:Create new Course
		creatorUserPageObj.createCourse(objListOFTestDataForSunbird);
		
		GenericFunctions.waitWebDriver(2000);
		
		//Step 4:Save and Send for Review
		creatorUserPageObj.saveAndSendCourseForReview(objListOFTestDataForSunbird);

		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		
		//Step 4a:Check for course in review submissions 
		creatorUserPageObj.reviewInSubmissions(COURSE,objListOFTestDataForSunbird);

		GenericFunctions.waitWebDriver(3000);
		
		//Logout as Creator
		signupObj.userLogout();
		
		
		//Step 5:Login as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//Step 6 and 7:Login as reviewer and Reject the Content -Course 	
		creatorUserPageObj.rejectTheContent(COURSE);
		
		GenericFunctions.waitWebDriver(2000);
		
		//Logout as Reviewer again 
		signupObj.userLogout();
		
		//Step 8:Login As Creator
		signupObj.userLogin(CREATOR);
		
		//Step 9,10 and 11:Go to Drafts, Edit and Re submit the content for review
		creatorUserPageObj.editAndSubmitContent();
		
		//wait for 2 sec
		GenericFunctions.waitWebDriver(2000);
		
		//Logout as Creator after Resubmitting the content for Review
		signupObj.userLogout();
		
		//Step 12:Login as Reviewer , 
		signupObj.userLogin(REVIEWER);
		
		//Step 13:Navigation to Up for review and then publish the content
		creatorUserPageObj.searchInUpForReview(COURSE, objListOFTestDataForSunbird);
		
		GenericFunctions.waitWebDriver(2000);
		
		//Step 14:Go to up for review and publish the content
		creatorUserPageObj.publishAndSearch(COURSE,objListOFTestDataForSunbird);
		
		//wait for 2 sec
		GenericFunctions.waitWebDriver(2000);
		
		//Logout as Reviewer
		signupObj.userLogout();
	}

}
