/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/09/2018
* Purpose: Upload sub org
*/


package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.pageobjects.UploadOrgObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;


public class Admin_TC02 extends BaseTest  
{

	@Test(priority=3)
	public void uploadSubOrgTestCase02() throws InterruptedException, Exception 
	{		
		//Step1: Admin Login
		SignUpObj adminLogin = new SignUpObj();
		adminLogin.userLogin(ADMIN);
		
		//Step2 and 3: Upload Sub Org
		UploadOrgObj adminUpload= new UploadOrgObj();
		boolean actualValue=adminUpload.uploadRootAndSubOrg(UPLOAD_SUB_ORG);
		AssertJUnit.assertEquals(true,actualValue);
		
		GenericFunctions.waitWebDriver(2000);
		//Logout as Admin
		adminLogin.userLogout();
	}

}
