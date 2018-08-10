package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Reviewer_TC01 extends BaseTest
{
	@Test
	public void testCase01() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		 
		//Login as content creator
		signupObj.userLogin(CREATOR);
		
		//create a course
		creatorUserPageObj.createResource(objListOFTestDataForSunbird);
		
		//Submit the course for review 
		creatorUserPageObj.saveAndSendCourseForReview(objListOFTestDataForSunbird);
	}

}
