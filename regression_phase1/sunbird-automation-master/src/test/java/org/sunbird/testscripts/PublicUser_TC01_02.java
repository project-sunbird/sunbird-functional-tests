/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/17/2018
* Purpose: Verify Login and Change password
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.SignUp;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;


public class PublicUser_TC01_02 extends BaseTest
{
		
	@Test
	public void loginAndChangePasswordTestCase01_02() throws Exception 
	{	
		SignUpObj publicLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1: Login into Application as Public user
		publicLogin.userLogin(PUBLICUSER);
		
		//Step 2: Verify Login 
		//And Change Password
		publicUser.changePassword();
	}

}
