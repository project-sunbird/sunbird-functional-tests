/**

* Created by Qualitrix Technologies Pvt Ltd.

* @author: Abhinav kumar singh

* Date: 07/16/2018

* Purpose: After creating invite only batch,check invited member can update it.

*/
package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.CreateMentorPage;
import org.sunbird.page.CreatorUserPage;
import org.sunbird.pageobjects.CreateMentorPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Mentor_TC06 extends BaseTest 
{
	CreatorUserPage createUserPage=PageFactory.initElements(driver, CreatorUserPage.class);
	CreateMentorPage createMentorPage=PageFactory.initElements(driver, CreateMentorPage.class);
	List <TestDataForSunbird> objListOFTestDataForSunbird= null;
	
	@Test
	public void updateBatchAsMemberInMentor_TC06() throws Exception
	{
		
		SignUpObj creatorLogin = new SignUpObj();
		CreateMentorPageObj createMentorPageObj =new CreateMentorPageObj();
		
		//Step1: Login as mentor
		
		creatorLogin.userLogin(MENTOR_S);
		
		
		//Step2: search for the course created by other user and create invite only batch
		
		String courseName=createMentorPageObj.createInviteOnlyBatch();
		System.out.println(courseName);
		
		
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(1000);
		
		//Step3: Logout as mentor
		
		creatorLogin.userLogout();
		
		
		//Step4: Login as invited member  
			
		creatorLogin.userLogin(PUBLICUSER_S);
		
		
		//Step5: Search for the particular course 
		
		createMentorPageObj.navigateToCourseAndSearch(courseName);
		
		//Step6: Logout as invited member
		
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(1000);
		creatorLogin.userLogout();
		
		//Step7: Login as mentor
		
		creatorLogin.userLogin(MENTOR_S);
		
		//Step8: Search for the particular course and update the batch
		
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		createMentorPageObj.navigateToCourseSearchAndUpdate();
		String expected = objListOFTestDataForSunbird.get(1).getCourseName()+ GenericFunctions.readFromNotepad(".//batchName.txt");
		System.out.println(expected);
		
		//Step9: Logout as mentor
		
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();
		GenericFunctions.waitWebDriver(1000);
		creatorLogin.userLogout();
		
		//Step10: Login as invited member  
		
		creatorLogin.userLogin(PUBLICUSER_S);
				
				
		//Step11: Search for the particular course 
				
		createMentorPageObj.navigateToCourseAndSearch(courseName);
		//GenericFunctions.waitWebDriver(2000);
//		createMentorPage.closeBatchIcon.click();
//		GenericFunctions.waitWebDriver(2000);
//		String Actual = createMentorPage.batchDetails.getText();
//		System.out.println(Actual);
//		
//		//Step11: Verify the update 
//
//		Assert.assertEquals(Actual, expected);
//		System.out.println("updated data is verified");
	}
}
