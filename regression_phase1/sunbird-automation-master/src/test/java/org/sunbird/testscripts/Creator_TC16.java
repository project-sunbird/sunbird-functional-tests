/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/26/2018
* Purpose: Published content shows in "Published" bucket
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

public class Creator_TC16 extends BaseTest
{
	@Test(priority=16, groups={"Creator Group"})
	public void verifyPublishedBucketTestCase16() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");

		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();

		//Step 1:Login as Creator
		signupObj.userLogin(CREATOR);

		//Step 2:Navigate to workspace to Create a course
		creatorUserPageObj.navigateToWorkspace(COURSE);

		//Step 3:Create a Course
		creatorUserPageObj.createCourse(objListOFTestDataForSunbird);

		//Step 4:Save and submit the coursed for Review
		creatorUserPageObj.saveAndSendCourseForReview(objListOFTestDataForSunbird);

		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		
		//Logout as creator
		signupObj.userLogout();
		
		//Step 5:Login as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//wait for 2 sec
		GenericFunctions.waitWebDriver(2000);
		
		//Step 6:Search the course in Up for review		
		creatorUserPageObj.searchInUpForReview(COURSE,objListOFTestDataForSunbird);
		
		//Step 7:Publish the course 
		creatorUserPageObj.publishAndSearch(COURSE,objListOFTestDataForSunbird);
		
		//Logout as Reviewer
		signupObj.userLogout();
		
		//Step 8:Login as Creator
		signupObj.userLogin(CREATOR);
		
		//Step 8a:Checked the publishes course in Published Bucket
		creatorUserPageObj.checkInPublished(COURSE,objListOFTestDataForSunbird);
		
		//Logout as Creator
		signupObj.userLogout();
	}

}
