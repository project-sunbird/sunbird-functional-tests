/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/11/2018
* Purpose: Upload users for sub orgs with org id
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.pageobjects.UploadOrgObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Admin_TC04 extends BaseTest
{
	@Test(priority=4)
	public void subOrgUsersTestCase04() throws Exception
	{
		//Step1: Admin Login
		SignUpObj adminLogin = new SignUpObj();
		adminLogin.userLogin(ADMIN);

		//Step2,3,4 and 5:Go to profile and 
		//Upload Users for Sub Org with sub Org Id
		UploadOrgObj adminUpload= new UploadOrgObj();
		adminUpload.uploadRootAndSubOrgUserWithOrgId(UPLOAD_USERS_SUB_ORG);
		
		
		//Logout as Admin
		adminLogin.userLogout();
		
	}
}
