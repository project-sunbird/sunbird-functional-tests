/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/15/2018
* Purpose: Admin dashboard - creation and CSV download
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.pageobjects.UploadOrgObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Admin_TC08_10 extends BaseTest
{
	@Test(priority=8)
	public void adminCreationAndCsvTestCaseTC08_10() throws Exception
	{	
		SignUpObj adminLogin = new SignUpObj();
		UploadOrgObj adminUpload= new UploadOrgObj();
		
		//Step1: Login as Admin
		adminLogin.userLogin(ADMIN);

		//Step 2,3,4 and 5: Click on admin dashboard.
		//Apply filter, check stats 
		//Download the CSV
		adminUpload.adminCreationConsumption(FILTER_CREATION);
	
		//Logout as Admin
		adminLogin.userLogout();
	}
	


}
