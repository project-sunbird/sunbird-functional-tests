/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/21/2018
* Purpose: To verify Course search - with filter
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;


public class PublicUser_TC08 extends BaseTest
{
	@Test
	public void courseSearchFilterTestCase08() throws Exception 
	{	

		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1:Login as Public user
		userLogin.userLogin(PUBLICUSER1);
		
		//Step 2 and 3:Course search with filter
		publicUser.courseSearchFilter();
		
		//Logout as Public user
		userLogin.userLogout();
	}
	

}
