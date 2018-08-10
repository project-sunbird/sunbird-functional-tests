package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Reviewer_TC14 extends BaseTest
{
	@Test
	public void testCase16() throws Exception
	{
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		SignUpObj signupObj = new SignUpObj();
		
		/*
		//Step1: Login as Creator
		signupObj.userLogin(CREATOR);

		//Go to workspace , create a book,submit for reviwe
		creatorUserPageObj.createLessonPlan();
		
		//Save and send for Review
		creatorUserPageObj.saveAndPublishLesson();
		
		//Logout as Creator
		signupObj.userLogout();
		*/
		//Login as content creator
		signupObj.userLogin(REVIEWER);
		
		//Navigate to workspace and apply filter and search the content type-Lesson plan
		creatorUserPageObj.searchWithFilters();
	}

}
