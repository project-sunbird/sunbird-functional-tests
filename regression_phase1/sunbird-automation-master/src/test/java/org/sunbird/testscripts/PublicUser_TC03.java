/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/18/2018
* Purpose: To verify Forgot password functionality
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC03 extends BaseTest
{
	@Test
	public void forgotPasswordTestCase03() throws Exception 
	{
		SignUpObj publicLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
	
		GenericFunctions.waitWebDriver(3000);
		
		//Step 1,2,3 and 4: To verify Forgot Password
		//and confirm the instructions received
		publicUser.forgotPassword();
	}
}
