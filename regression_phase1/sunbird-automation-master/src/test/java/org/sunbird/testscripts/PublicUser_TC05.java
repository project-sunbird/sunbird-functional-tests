/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/18/2018
* Purpose: To verify course search
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC05 extends BaseTest
{
	@Test
	public void searchCourseTestCase05() throws Exception
	{
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1 : Login into Application as Public user	
		userLogin.userLogin(PUBLICUSER1);

		//Step 2 and 3 : Click on courses
		//Search for course
		publicUser.courseSearch();
		
		//Logout as Public user
		userLogin.userLogout();
	
	}

}
