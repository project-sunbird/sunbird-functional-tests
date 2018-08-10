/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/19/2018
* Purpose:  Upload Content type-Webm and validate it
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Creator_TC05 extends BaseTest
{
	@Test(priority=5, groups={"Creator Group"})
	public void uploadWebmTestCase05() throws Exception
	{
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1 :Login as content creator
		signupObj.userLogin(CREATOR);
		
		//Step 2,3 and 4:Navigate to workspace and upload content of type "webm"
		creatorUserPageObj.uploadContentMp4(WEBM);
		
		//Logout as creator
		signupObj.userLogout();
		
		//Step 5:Login as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//Step 6 and 7:Go to workspace and publish content webm
		creatorUserPageObj.goToWorkspace("webm");
		
		GenericFunctions.waitWebDriver(2000);
		
		//Reject the Upload type-WEBM
		creatorUserPageObj.rejectTheUploads(WEBM);
		
		//Logout as reviewer
		signupObj.userLogout();
	}

}
