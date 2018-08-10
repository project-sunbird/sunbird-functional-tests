/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/14/2018
* Purpose: Create a New Lesson plan and validate it
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Creator_TC03 extends BaseTest
{
	@Test(priority=3, groups={"Creator Group"})
	public void lessonplanCreationTestCase03() throws Exception
	{
		//Step1: Login as Creator
		SignUpObj signupObj = new SignUpObj();
		signupObj.userLogin(CREATOR);

		//Step 2 and 3: Go to workspace , create a lesson plan
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		creatorUserPageObj.createLessonPlan();
		
		//Step 4: Submit the lesson plan for Review
		creatorUserPageObj.saveAndPublishLesson();
		
		//Logout as Creator
		signupObj.userLogout();
		
		//Step 5:Logout as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//Step 6,7 and 8:Go to Workspace, Publish and 
		creatorUserPageObj.goToWorkspace("lessonplan");
		
		GenericFunctions.waitWebDriver(2000);
		
		//reject the lesson plan from the existing list
		creatorUserPageObj.rejectTheContent("LESSONA");
	
		//Logout as Reviewer
		signupObj.userLogout();
		
	}


}
