/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/05/2018

* Purpose:Navigate to my Activity .

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;


public class Mentor_TC07 extends BaseTest {
	
	@Test
	public void NavigateToMyActivityMentorTC_07() throws Exception
	{
		
		//Step1: Login as Creator
		
		SignUpObj creatorLogin = new SignUpObj();
		creatorLogin.userLogin(MENTOR_S);

		
		//Step2: Navigate to My activity in Dropdown
		
		CreateMentorPageObj createMentorPageObj =new CreateMentorPageObj();
		createMentorPageObj.navigateToMyActivityInDropDownMenu();
		System.out.println("inside My activity");
		
		
		
	}
	

}
