/**
* Created by Qualitrix Technologies Pvt Ltd.
* @author: Ajith Manjunath
* Date: 06/12/2018
* Purpose: Create a New Book and validate it
*/

package org.sunbird.testscripts;

import org.testng.annotations.Test;
import java.util.List;

import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.pageobjects.CreatorUserPageObj;
import org.sunbird.pageobjects.SignUpObj;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class Creator_TC02 extends BaseTest
{
	@Test(priority=2, groups={"Creator Group"})
	public void bookCreationTestCase02() throws Exception
	{	
		List <TestDataForSunbird> objListOFTestDataForSunbird= null ;
		objListOFTestDataForSunbird = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcourse");
		
		//Step 1: Login as Creator
		SignUpObj signupObj = new SignUpObj();
		signupObj.userLogin(CREATOR);

		//Go to workspace , create a book,submit for reviewer
		CreatorUserPageObj creatorUserPageObj = new CreatorUserPageObj();
		creatorUserPageObj.navigateToWorkspace(BOOK);

		//Step 3: Create new Book
		creatorUserPageObj.createBook(objListOFTestDataForSunbird);

		//Step 4: Save and Send for Review
		creatorUserPageObj.saveAndSendBookForReview(objListOFTestDataForSunbird);
		GenericFunctions.waitWebDriver(2000);
		GenericFunctions.refreshWebPage();

		//Step 4a: Check for course in review submissions 
		creatorUserPageObj.reviewInSubmissions(BOOK, objListOFTestDataForSunbird);
		
		//Logout as creator
		signupObj.userLogout();

		//Step 5: Login as reviewer
		signupObj.userLogin(BOOKREVIEWER);

		//Step 6:Check in Up for Review ,publish and reject a Book
		creatorUserPageObj.searchInUpForReview(BOOK,objListOFTestDataForSunbird);
		
		//Step 7:Publish the book
		creatorUserPageObj.publishAndSearch(BOOK,objListOFTestDataForSunbird);
		
		GenericFunctions.waitWebDriver(2000);
		
		//Reject the book from the existing list
		creatorUserPageObj.rejectTheContent("BOOKA");
		
		//Logout as reviewer
		signupObj.userLogout();
	}

}
