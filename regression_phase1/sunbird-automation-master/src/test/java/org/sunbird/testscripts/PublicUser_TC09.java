/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/22/2018
* Purpose: To verify Library search - with filter
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC09 extends BaseTest
{
	@Test
	public void librarySearchFilterTestCase09() throws Exception
	{	
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1 : Login in as Public user
		userLogin.userLogin(PUBLICUSER1);
		
		//Step 2 and 3: Click on library
		//Click on filters
		publicUser.librarysearchFilter();
		
		//Logout as Public user
		userLogin.userLogout();
	}

}
