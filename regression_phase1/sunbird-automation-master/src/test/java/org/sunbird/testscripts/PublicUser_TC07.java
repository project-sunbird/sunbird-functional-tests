/**
 * Created by Qualitrix Technologies Pvt Ltd.
 * @author: Ajith Manjunath
 * Date: 05/21/2018
 * Purpose: To verify flag a content
 */

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC07 extends BaseTest
{
	@Test
	public void flagContentTestCase07() throws Exception
	{
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1:Login as Public user
		userLogin.userLogin(PUBLICUSER1);
		
		//Step
		publicUser.contentFlag();
		
		//Logout as Public user
		userLogin.userLogout();


//Test case is not valid as the Flag Icon is removed from the application
	}

}
