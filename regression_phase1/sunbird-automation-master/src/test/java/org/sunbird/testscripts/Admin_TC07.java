/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/15/2018
* Purpose: Admin dashboard
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.pageobjects.UploadOrgObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Admin_TC07 extends BaseTest
{
	@Test(priority=7)
	public void adminDashboardTestCase07() throws Exception
	{
		SignUpObj adminLogin = new SignUpObj();
		UploadOrgObj adminUpload= new UploadOrgObj();

		//Step 1: Login as Admin
		adminLogin.userLogin(ADMIN);

		//Step 2,3,4 and 5 : Go to Profile page
		//Verify Admin DashBoard
		adminUpload.adminDashboard();
		
		//Logout as Admin
		adminLogin.userLogout();

	}


}
