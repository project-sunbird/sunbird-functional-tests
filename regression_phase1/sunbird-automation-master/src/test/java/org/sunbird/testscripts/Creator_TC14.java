/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/25/2018
* Purpose: Content Limited Sharing
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Creator_TC14 extends BaseTest
{
	@Test(priority=14, groups={"Creator Group"})
	public void contentLimitedSharingTestCase14() throws Exception
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1:Login as Content creator
		signupObj.userLogin(CREATOR);
		
		//Step 2:Navigate to workspace and create all the content types
		creatorUserPageObj.navigateToWorkspace(BOOK);
		
		//Step 3e:Create content type -BOOK
		creatorUserPageObj.createBook(objListOFTestDataForSunbird);
		
		//Step 3eSend BOOK for Limited Sharing
		creatorUserPageObj.limitedSharing("Book");
		
		//Step 3a:Navigate to workspace to Create a course 
		creatorUserPageObj.navigateToWorkspace(COURSE);
		
		//Step 3a:create content type-COURSE
		creatorUserPageObj.createCourse(objListOFTestDataForSunbird);
		
		//Step 3a:Send COURSE for Limited Sharing
		creatorUserPageObj.limitedSharing("course");
		
		
			
		//Step 3c:Navigate to workspace to Create a LESSON PLAN
		//AND
		//Step 3c:Create content type-Lesson plan
		creatorUserPageObj.createLessonPlan();
		
		//Step 3c:Send the LESSON PLAN for Limited sharing
		creatorUserPageObj.limitedSharing("lesson plan");
		
		//Step 3d:Navigate to workspace to create a Resource
		creatorUserPageObj.navigateToWorkspace(RESOURCE);
		
		//Step 3d:Create content type-Resource
		creatorUserPageObj.createResource(objListOFTestDataForSunbird);
		
		//Step 3d:Send the RESOURCE for Limited sharing
		creatorUserPageObj.limitedSharing("resource");
		
		
		//Step 3f:Navigate to workspace to Upload Content-
		creatorUserPageObj.navigateToWorkspace(UPLOADCONTENT);
		
		//Step 3f:Create content type-EPUB
		creatorUserPageObj.uploadContentLimitedSharing(EPUB);
		
		//Step 3f:Send the EPUB content for Limited sharing
		creatorUserPageObj.limitedSharing("upload");
		
		//Creator Logout
		signupObj.userLogout();
		
	}

}
