/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/18/2018
* Purpose: To verify Library search- with filter
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC06  extends BaseTest
{
	@Test
	public void librarySearchTestCase06() throws Exception 
	{
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1: Login as Mentor
		userLogin.userLogin(MENTOR);
		
		//Step 1a,2,3,4 and 5:
		//Search for book, Resource, Upload content
		//Collection and lesson plan
		publicUser.librarySearch();
		
		//Logout as Public user
		userLogin.userLogout();
				
	}	

}
