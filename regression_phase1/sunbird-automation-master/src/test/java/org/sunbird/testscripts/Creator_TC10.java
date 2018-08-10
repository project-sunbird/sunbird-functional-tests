/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/20/2018
* Purpose: Create a New Collection and validate it
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Creator_TC10 extends BaseTest
{
	@Test(priority=10, groups={"Creator Group"})
	public void collectionCreationTestCase10() throws Exception
	{
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1:Login as content creator
		signupObj.userLogin(CREATOR);
		
		//Step 2,3 and 4:Navigate to workspace and create a Collection 
		//And send the Collection for review
		creatorUserPageObj.createCollection();	
		
		//Logout as Creator 
		signupObj.userLogout();
		
		//Step 5:Login as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//Step 6 and 7:Go to workspace and publish the content-h5p
		creatorUserPageObj.goToWorkspace("collection");
		
		GenericFunctions.waitWebDriver(2000);
		
		//Reject the Collection
		creatorUserPageObj.rejectTheContent(COLLECTION);
		
		//Logout as Reviewer
		signupObj.userLogout();
	}

}
