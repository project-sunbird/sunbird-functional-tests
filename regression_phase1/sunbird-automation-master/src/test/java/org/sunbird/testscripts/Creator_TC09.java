/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/20/2018
* Purpose: Upload content type-H5P and validate it
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Creator_TC09  extends BaseTest
{
	@Test(priority=9, groups={"Creator Group"})
	public void uploadH5pTestCase09() throws Exception
	{
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1:Login as content creator
		signupObj.userLogin(CREATOR);
		
		//Step 2,3 and 4:Navigate to workspace and upload content type-H5P
		creatorUserPageObj.uploadContentMp4(H5P);
		
		//Logout as creator
		signupObj.userLogout();
		
		//Step 5:Login as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//Step 6 and 7:Go to workspace and publish the content-h5p
		creatorUserPageObj.goToWorkspace("h5p");
		
		GenericFunctions.waitWebDriver(2000);
		
		//reject the upload type -H5P
		creatorUserPageObj.rejectTheUploads(H5P);
		
		//Logout as Reviewer
		signupObj.userLogout();
	}

}
