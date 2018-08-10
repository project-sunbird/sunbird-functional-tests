/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/23/2018
* Purpose: To verify Profile - all information update
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC11 extends BaseTest
{
	@Test
	public void profileInfoUpdateTestCase11() throws Exception
	{
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		
		//Step 1 : Login as any user	
		userLogin.userLogin(PUBLICUSER1);
		 
		//Step 2: Profile - all information update	
		publicUser.profileInformationUpdate();
		
		//Logout as Public user
		userLogin.userLogout();
		
	}
	
}
