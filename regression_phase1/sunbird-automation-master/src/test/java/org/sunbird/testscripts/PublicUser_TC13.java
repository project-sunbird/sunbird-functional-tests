/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/23/2018
* Purpose: Search for Orgs
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC13 extends BaseTest
{
	@Test
	public void searchOrgsTestCase13() throws Exception
	{
		
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1 :Login as any user(MENTOR)
		userLogin.userLogin(MENTOR);

		//Step 2:Search for Orgs	
		publicUser.searchOrgs();
		
		//Logout as Public user
		userLogin.userLogout();

	}

}
