/**
 * Created by Qualitrix Technologies Pvt Ltd.
 * @author: Ajith Manjunath
 * Date: 05/16/2018
 * Purpose: Admin dashboard - consumption and CSV download
 */

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.pageobjects.UploadOrgObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Admin_TC09_11 extends BaseTest 
{
	@Test(priority=9)
	public void adminConsumptionAndCsvTestCase09_11() throws Exception
	{
		SignUpObj adminLogin = new SignUpObj();
		UploadOrgObj adminUpload= new UploadOrgObj();

		//Step1: Login as Admin
		adminLogin.userLogin(ADMIN);

		//Step 2,3,4 and 5: Click on admin dashboard.
		//Apply filter, check stats 
		//Download the CSV
		adminUpload.adminCreationConsumption(FILTER_CONSUMPTION);

		//Logout as Admin
		adminLogin.userLogout();
	}


}
