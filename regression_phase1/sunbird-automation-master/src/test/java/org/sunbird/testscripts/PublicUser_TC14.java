/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 05/28/2018
* Purpose: To verify skill Endorsement of user
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.PublicUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class PublicUser_TC14 extends BaseTest
{
	@Test
	public void skillEndorsementTestCase14() throws Exception
	{
		
		SignUpObj userLogin = new SignUpObj();
		PublicUserPageObj publicUser = new PublicUserPageObj();
		
		//Step 1:Login as any user
		userLogin.userLogin(PUBLICUSER1);
		
		//Step 2 : Go to profile and add a skill to public user
		String username=publicUser.addSkill();
		
		//Step 3 : Logout as Public user
		userLogin.userLogout();
		
		GenericFunctions.waitWebDriver(2000);
		//String username= "Test firstname  Test lastname";
		
		//Step 4 : Login as Creator
		userLogin.userLogin(CREATOR);
		
		//Step 5:Go to profile 
		//Search any profile and generate a random skill 
		//Search for a profile and Endorse the skill 
		publicUser.skillEndorsement(username);
		
		//Logout as Public user
		userLogin.userLogout();
	}
	
}
