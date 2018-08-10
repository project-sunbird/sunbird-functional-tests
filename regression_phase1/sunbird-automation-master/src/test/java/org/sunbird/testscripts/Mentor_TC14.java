/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/19/2018

* Purpose: Create open batch for a course created by you ,public user should enroll to it.

*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.testng.annotations.Test;

public class Mentor_TC14 extends BaseTest
{
	@Test
	public void enrollToCourseInOpenBatchMentor_TC14() throws Exception
	{
		
		
		SignUpObj userLogin = new SignUpObj();
		CreateMentorPageObj createMentorPageObj=new CreateMentorPageObj();
		
		
		
		//Step1: Login as Mentor
		
		//userLogin.userLogin(MENTOR_S);
		userLogin.userLogin(MENTOR);
		
		//Step2: Search for a course created by same mentor
		
		createMentorPageObj.navigateToCourseAndSearchForOpenBatch();
		
		
		//Step3:create an open batch
		
		createMentorPageObj.createOpenBatch();
		
		GenericFunctions.waitWebDriver(2500);
		
		//Step4 Logout as Mentor
		
		userLogin.userLogout();
		GenericFunctions.waitWebDriver(2000);
		
		
		//Step5:Login as Public user	
		
		userLogin.userLogin(PUBLICUSERS_S);
				
				
		//Step6:Search for the course
				
		createMentorPageObj.navigateToCourseAndSearchForOpenBatch();
		
		
		//Step7:Enroll for the open batch
		
		createMentorPageObj.enrollForOpenBatch();
		
		//Step8:Logout as public user 
		userLogin.userLogout();
		
	}
	
	
}
