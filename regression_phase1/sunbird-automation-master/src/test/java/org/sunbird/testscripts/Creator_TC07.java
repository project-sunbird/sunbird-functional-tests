/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/20/2018
* Purpose: Upload content type-Epub and validate it
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Creator_TC07 extends BaseTest
{
	@Test(priority=7, groups={"Creator Group"})
	public void uploadEpubTestCase07() throws Exception
	{
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1:Login as content creator
		signupObj.userLogin(CREATOR);
		
		//Step 2,3 and 4:Navigate to workspace and upload content type-EPUB
		creatorUserPageObj.uploadContentMp4(EPUB);
		
		//Logout as creator
		signupObj.userLogout();
			
		//Step 5:Login as Reviewer
		signupObj.userLogin(REVIEWER);
		
		//Step 6 and 7:Go to workspace and publish the content-epub
		creatorUserPageObj.goToWorkspace("epub");
		
		GenericFunctions.waitWebDriver(2000);
		
		//reject the upload type -EPUB
		creatorUserPageObj.rejectTheUploads(EPUB);
		
		//Logout as Reviewer
		signupObj.userLogout();
	}

}
