/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/27/2018
* Purpose: Book creator does not get content creation access
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Creator_TC20 extends BaseTest
{
	@Test(priority=19, groups={"Creator Group"})
	public void bookCreatortestCase20() throws Exception
	{
		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		
		//Step 1:Login as Book creator
		signupObj.userLogin(BOOKCREATOR);
		
		//Step 2,3 and 4:Navigate to workspace and verify only book option is present 
		creatorUserPageObj.verifyOnlyBookOption();
		
		//Logout as Creator
		signupObj.userLogout();
	}

}
