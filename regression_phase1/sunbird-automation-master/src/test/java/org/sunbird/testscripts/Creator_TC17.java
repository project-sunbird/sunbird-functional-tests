/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/26/2018
* Purpose: Dial code link to book unit
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Creator_TC17 extends BaseTest
{
	@Test(priority=17, groups={"Creator Group"})
	public void dialCodeLinkTestCase17() throws Exception
	{

		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");

		SignUpObj signupObj = new SignUpObj();
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();

		//Step 1:Login as Creator
		signupObj.userLogin(CREATOR);

		//Step 2:Navigate to workspace to Create a book
		creatorUserPageObj.navigateToWorkspace(BOOK);
		
		//Step 3,4:Create a Book
		creatorUserPageObj.createBook(objListOFTestDataForSunbird);
		
		//Step 5,6 and 7:Save the book and check the sucessful message
		creatorUserPageObj.saveBookAndCheckMessage();
		
		//Logout as Creator
		signupObj.userLogout();
	}
	
}
